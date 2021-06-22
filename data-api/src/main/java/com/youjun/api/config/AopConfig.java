package com.youjun.api.config;

import com.youjun.common.domain.WebLog;
import com.youjun.common.log.WebLogAspect;
import com.youjun.common.service.WebLogAspectService;
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


   /* final WebLogService webLogService;

    @Autowired
    public AopConfig(WebLogService webLogService) {
        this.webLogService = webLogService;
    }*/

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
//                webLog.setUsername(AdminUserDetails.getCurrentUser().getUserId());
//                return webLogService.save(webLog);
                return true;
            }
        };
    }
}
