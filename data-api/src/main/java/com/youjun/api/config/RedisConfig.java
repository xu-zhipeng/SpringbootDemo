package com.youjun.api.config;


import com.youjun.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置类
 * Created on 2020/3/2.
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
