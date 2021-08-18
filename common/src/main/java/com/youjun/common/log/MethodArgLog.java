package com.youjun.common.log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 *  操作日志 参数注解
 * </p>
 *
 * @author kirk
 * @since 2021/5/10
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodArgLog {
    String value() default "";
    String description() default "";
    boolean required() default true;
    Class<?> argClass() default Object.class;
}
