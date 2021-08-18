package com.youjun.common.log;

import java.lang.annotation.*;

/**
 * <p>
 *  操作日志注解
 * </p>
 *
 * @author kirk
 * @since 2021/5/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
    String value() default "";
    String description() default "";
    boolean required() default true;
    Class<?> argClass() default Object.class;
}
