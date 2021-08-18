package com.youjun.api.modules.ums.service;

import com.youjun.api.modules.ums.dto.CaptchaDTO;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/7/12
 */
public interface CacheService {
    /**
     * 缓存验证码
     * @param captchaDTO
     * @return
     */
    CaptchaDTO setCapthca(CaptchaDTO captchaDTO);

    /**
     * 获取验证码
     * @param captchaId
     * @return
     */
    CaptchaDTO getCapthca(String captchaId);

    /**
     * 删除验证码
     * @param captchaId
     */
    void delCapthca(String captchaId);
}
