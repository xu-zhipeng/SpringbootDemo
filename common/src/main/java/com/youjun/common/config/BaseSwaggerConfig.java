package com.youjun.common.config;

import com.youjun.common.domain.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Swagger基础配置
 * Created on 2020/7/16.
 */
public abstract class BaseSwaggerConfig {
    private final static String HTTP = "http";
    private final static String HTTP_PORT = ":80";
    private final static String HTTPS = "https";
    private final static String HTTPS_PORT = ":443";

    @Bean
    public Docket createRestApi() {
        Set<String> set = new HashSet<>();
        set.add("https");
        set.add("http");
        SwaggerProperties swaggerProperties = swaggerProperties();
        Docket docket = new Docket(DocumentationType.OAS_30)
                //支持的协议
                .protocols(set)
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                //包扫描地址
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                .paths(PathSelectors.any())
                .build();
        if (swaggerProperties.isEnableSecurity()) {
            docket.securitySchemes(securitySchemes()).securityContexts(securityContexts());
        }
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        //设置请求头信息
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        return Collections.singletonList(apiKey);
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        return Collections.singletonList(getContextByPath("/*/.*"));
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

    /**
     * 自定义Swagger配置
     */
    public abstract SwaggerProperties swaggerProperties();

    /**
     * swagger servers 地址 http改https
     *
     * @return
     */
    @Bean
    public WebMvcOpenApiTransformationFilter springFoxSwaggerHostResolver() {
        return new WebMvcOpenApiTransformationFilter() {
            @Override
            public boolean supports(DocumentationType documentationType) {
                return documentationType.equals(DocumentationType.OAS_30);
            }

            @Override
            public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
                HttpServletRequest request = context.request().get();
                OpenAPI swagger = context.getSpecification();
                String scheme = "http";
                String referer = request.getHeader("Referer");
                if (StringUtils.hasLength(referer)) {
                    //获取协议
                    scheme = referer.split(":")[0];
                }
                List<Server> servers = new ArrayList<>();
                String finalScheme = scheme;
                //重新组装server信息
                swagger.getServers().forEach(item -> {
                    //替换协议,去掉默认端口
                    item.setUrl(urlHandle(item.getUrl().replace("http", finalScheme)));
                    servers.add(item);
                });
                swagger.setServers(servers);
                return swagger;
            }
        };
    }

    /**
     * url处理
     * @param url
     * @return
     */
    private String urlHandle(String url) {
        if (url.contains(HTTP)) {
            url = url.replace(HTTPS_PORT, HTTP_PORT);
        }
        if (url.contains(HTTPS)) {
            url = url.replace(HTTP_PORT, HTTPS_PORT);
        }
        return url;
    }
}
