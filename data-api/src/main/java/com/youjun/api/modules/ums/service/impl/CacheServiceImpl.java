package com.youjun.api.modules.ums.service.impl;

import com.youjun.api.modules.ums.dto.CaptchaDTO;
import com.youjun.api.modules.ums.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/7/13
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "ums_cache")
public class CacheServiceImpl implements CacheService {
    @Override
    @CachePut(key = "#captcha.captchaId")
    public CaptchaDTO setCapthca(CaptchaDTO captcha){
        log.info("captcha value is: "+captcha.getCaptchaValue());
        log.info("存储验证码信息");
        return captcha;
    }

    @Override
    @Cacheable(key = "#captchaId")
    public CaptchaDTO getCapthca(String captchaId){
        log.info("没有缓存");
        return null;
    }

    @Override
    @CacheEvict(key = "#captchaId")
    public void delCapthca(String captchaId){
    }
}
