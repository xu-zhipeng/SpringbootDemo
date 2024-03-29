package com.youjun.api.config;

import com.youjun.api.domain.AdminUserDetails;
import com.youjun.api.modules.log.service.WebLogService;
import com.youjun.common.aspect.WebLogAspect;
import com.youjun.common.domain.WebLog;
import com.youjun.common.service.WebLogAspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

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


    final WebLogService webLogService;

    @Autowired
    public AopConfig(WebLogService webLogService) {
        this.webLogService = webLogService;
    }

    /**
     * 日志切面
     *
     * @return
     */
    @Bean
    public WebLogAspect webLogAspect(WebLogAspectService webLogAspectService) {
        return new WebLogAspect(webLogAspectService);
    }

    @Bean
    public WebLogAspectService webLogAspectService() {
        return new WebLogAspectService() {
            @Override
            public Boolean save(WebLog webLog) {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String userId=null;
                if(principal instanceof AdminUserDetails){
                    AdminUserDetails userDetails= (AdminUserDetails) principal;
                    userId = Optional.ofNullable(userDetails.getUserId()).orElse(null);
                }
                webLog.setUsername(userId);
                return webLogService.save(webLog);
            }
        };
    }
}
