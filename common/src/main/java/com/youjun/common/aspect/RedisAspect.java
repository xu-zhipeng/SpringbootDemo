/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.youjun.common.aspect;

import com.youjun.common.exception.ApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
public class RedisAspect {
    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 是否开启redis缓存  true开启   false关闭
     */
    @Value("${spring.redis.enabled:false}")
    private boolean enabled;

    @Around("execution(* com.youjun.common.service.RedisService.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if(enabled){
            try{
                result = point.proceed();
            }catch (Exception e){
                log.error("redis error", e);
                throw new ApiException("Redis服务异常");
            }
        }
        return result;
    }
}
