package com.youjun.common.config.xss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.youjun.common.util.JsonUtils;
import com.youjun.common.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ServletRequest包装类,对request做XSS过滤处理
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
     */
    HttpServletRequest orgRequest;
    /**
     * html过滤
     */
    private final static HTMLFilter htmlFilter = new HTMLFilter();

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    @Override
    public String getHeader(String name) {
        return xssEncode(super.getHeader(name));
    }

    @Override
    public String getQueryString() {
        return xssEncode(super.getQueryString());
    }

    @Override
    public String getParameter(String name) {
        return xssEncode(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = xssEncode(values[i]);
            }
            return escapseValues;
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
            }
            map.put(entry.getKey(), values);
        }
        return map;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String str = getRequestBody(super.getInputStream());
        Map<String, Object> map = null;
        try {
            map = JsonUtils.mapper.readValue(str, Map.class);
        } catch (JsonProcessingException e) {
            //单纯一个字符串 没有key
            str = xssEncode(str);
        }
        if (Objects.nonNull(map)) {
            Map<String, Object> resultMap = new HashMap<>(map.size());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if (val instanceof String) {
                    resultMap.put(key, xssEncode(val.toString()));
                } else {
                    resultMap.put(key, val);
                }
            }
            str = JsonUtils.mapper.writeValueAsString(resultMap);
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
    }

    private String getRequestBody(InputStream stream) {
        String line = "";
        StringBuilder body = new StringBuilder();
        // 读取POST提交的数据内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        try {
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.toString();
    }

    /**
     * 不用common.lang3 StringEscapeUtils.escapeHtml4方法的话，可以用 xssEncode方法进行过滤
     *
     * @param input
     * @return
     */
    private String xssEncode(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        }
        return htmlFilter.filter(input);
    }

    /**
     * 获取最原始的request
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }

        return request;
    }
}
