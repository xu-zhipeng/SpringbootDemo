package com.youjun.api.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 获取验证码DTO类
 * </p>
 *
 * @author kirk
 * @since 2021/7/12
 */
@Data
@ApiModel(value = "CaptchaDTO", description = "验证码缓存对象")
public class CaptchaDTO {
    public CaptchaDTO() {
    }
    public CaptchaDTO(String captchaId, String captchaValue) {
        this.captchaId = captchaId;
        this.captchaValue = captchaValue;
    }

    @ApiModelProperty(value = "验证码id")
    private String captchaId;

    @ApiModelProperty(value = "验证码值")
    private String captchaValue;
}
