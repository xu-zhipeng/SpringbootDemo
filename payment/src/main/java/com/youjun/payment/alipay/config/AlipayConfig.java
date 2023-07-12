/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制
 */
package com.youjun.payment.alipay.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hupeng
 * @date 2020-05-13
 */

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayConfig {

    /** 主键 */
    //@TableId
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id")
    private Long id;


    /** 应用ID */
    // @Column(name = "app_id")
    private String appId;


    /** 编码 */
    // @Column(name = "charset")
    private String charset;


    /** 类型 固定格式json */
    // @Column(name = "format")
    private String format;


    /** 网关地址 */
    // @Column(name = "gateway_url")
    private String gatewayUrl;


    /** 异步回调 */
    // @Column(name = "notify_url")
    private String notifyUrl;


    /** 私钥 */
    // @Column(name = "private_key")
    private String privateKey;


    /** 公钥 */
    // @Column(name = "public_key")
    private String publicKey;


    /** 回调地址 */
    // @Column(name = "return_url")
    private String returnUrl;


    /** 签名方式 */
    // @Column(name = "sign_type")
    private String signType;


    /** 商户号 */
    // @Column(name = "sys_service_provider_id")
    private String sysServiceProviderId;


    public void copy(AlipayConfig source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
