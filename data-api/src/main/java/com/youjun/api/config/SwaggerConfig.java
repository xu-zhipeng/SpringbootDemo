package com.youjun.api.config;

import com.youjun.common.config.BaseSwaggerConfig;
import com.youjun.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.youjun.api.modules")
                .title("royal-canin项目")
                .description("royal-canin项目相关接口文档")
                .contactName("kirk")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
