package com.youjun.api.config;

import com.youjun.common.config.BaseSwaggerConfig;
import com.youjun.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created on 2018/4/26.
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.youjun.api.modules")
                .title("spring-boot-demo项目")
                .description("spring-boot-demo项目相关接口文档")
                .contactName("kirk")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
