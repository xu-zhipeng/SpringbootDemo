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
public @interface Log {
    String value() default "";
}
