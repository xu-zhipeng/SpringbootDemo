package com.youjun.common.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youjun.common.api.PageParam;
import com.youjun.common.domain.WebLog;
import com.youjun.common.service.WebLogAspectService;
import com.youjun.common.util.IpAddressUtil;
import com.youjun.common.util.StringUtils;
import com.youjun.common.util.TimeUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一日志处理切面
 *
 * @author kirk
 * @date 2021/5/01
 */
@Aspect
@Order(1)
public class WebLogAspect {
    private static final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    final String[] keys = {"password", "token", "content", "file", "request"};

    final Gson gson = new GsonBuilder().setExclusionStrategies(new JsonIgnoreFields(keys)).create();

    final WebLogAspectService webLogAspectService;

    @Autowired
    public WebLogAspect(WebLogAspectService webLogAspectService) {
        this.webLogAspectService = webLogAspectService;
    }

    @Pointcut("@annotation(com.youjun.common.log.MethodLog)")
    public void annotationLog() {
    }

    /**
     * 切面日志 暂时关闭
     */
    /*@Pointcut("execution(public * com.youjun..controller.*.*(..)) " +
            "&& !execution(public * com.youjun..controller.UmsAdminController.captcha(..))" +
            "&& !execution(public * com.youjun..controller.WebLogController.*(..))"
    )*/
    @Pointcut("execution(public * com.youjun..close.*.*(..))")
    public void aopLog() {
    }

    @Before("aopLog() || annotationLog()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @AfterReturning(value = "aopLog() || annotationLog()", returning = "ret")
    public void doAfterReturning(Object ret) {
    }

    @Around("aopLog() || annotationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求信息(通过Logstash传入Elasticsearch)
        WebLog webLog = new WebLog();
        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            webLog.setDescription(apiOperation.value());
        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        webLog.setIp(IpAddressUtil.getIpAddress(request));
        webLog.setMethod(request.getMethod());
        try {
            String parameter = getParameter(method, joinPoint.getArgs());
            webLog.setParameter(parameter);
        } catch (Exception e) {
            log.error("gson解析错误,uri={}", request.getRequestURI());
            webLog.setParameter("{\"errorMessage\":\"gson解析错误\"}");
            e.printStackTrace();
        }
        try {
            String response = getResponse(result);
            webLog.setResult(response);
        } catch (Exception e) {
            log.error("gson解析错误,uri={}", request.getRequestURI());
            webLog.setResult("{\"errorMessage\":\"gson解析错误\"}");
            e.printStackTrace();
        }
        webLog.setSpendTime((int) (endTime - startTime));
        webLog.setStartTime(TimeUtils.timestampToDateTime(startTime));
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(request.getRequestURL().toString());
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("url", webLog.getUrl());
        logMap.put("method", webLog.getMethod());
        logMap.put("parameter", webLog.getParameter());
        logMap.put("spendTime", webLog.getSpendTime());
        logMap.put("description", webLog.getDescription());
        //LOGGER.info("{}", JSONUtil.parse(webLog));
//        log.info(Markers.appendEntries(logMap), JSONUtil.parse(webLog).toString());
        webLogAspectService.save(webLog);
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
                continue;
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (StringUtils.isNotBlank(requestParam.value())) {
                    key = requestParam.value();
                }
                Object value = args[i];
                //如果是文件对象
                if (value instanceof MultipartFile[]) {
                    List<String> list = new ArrayList<>();
                    MultipartFile[] multipartFiles = (MultipartFile[]) value;
                    for (MultipartFile file : multipartFiles) {
                        list.add(file.getOriginalFilename());   //获取文件名
                    }
                    value = list;
                } else if (value instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) value;
                    value = file.getOriginalFilename();  //获取文件名
                }
                map.put(key, value);
                argList.add(map);
                continue;
            }
            //将MethodArgLog 自定义注解修饰的参数作为请求参数
            MethodArgLog methodArgLog = parameters[i].getAnnotation(MethodArgLog.class);
            if (methodArgLog != null && methodArgLog.required()) {
                argList.add(args[i]);
                continue;
            }
            //如果是分页请求
            if (args[i] instanceof PageParam) {
                argList.add(args[i]);
                continue;
            }
            //都不是 结束
        }
        if (argList.isEmpty()) {
            return null;
        } else if (argList.size() == 1) {
            return gson.toJson(argList.get(0));
        } else {
            return gson.toJson(argList);
        }
    }

    /**
     * 将返回值进行转换 json
     *
     * @param result
     * @return
     */
    private String getResponse(Object result) {
        //如果是文件对象
        if (result instanceof ResponseEntity) {
            result = "文件流对象";
        }
        return gson.toJson(result);
    }
}

/**
 * Gson序列化对象排除属性
 * 调用方法：
 * String[] keys = { "id" };
 * Gson gson = new GsonBuilder().setExclusionStrategies(new JsonKit(keys)).create();
 */
class JsonIgnoreFields implements ExclusionStrategy {
    String[] keys;

    public JsonIgnoreFields(String[] keys) {
        this.keys = keys;
    }

    @Override
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes arg0) {
        for (String key : keys) {
            if (key.equals(arg0.getName())) {
                return true;
            }
        }
        return false;
    }

}
