package com.youjun.api.config;

import com.youjun.common.log.WebLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/5/10
 */
@Configuration
public class AopConfig {
    /**
     * 日志切面
     * @return
     */
    @Bean
    public WebLogAspect webLogAspect(){
        return new WebLogAspect();
    }
}
