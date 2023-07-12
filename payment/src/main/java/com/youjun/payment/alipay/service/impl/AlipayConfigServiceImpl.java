/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制
 */
package com.youjun.payment.alipay.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.youjun.common.exception.ApiException;
import com.youjun.payment.alipay.config.AlipayConfig;
import com.youjun.payment.alipay.service.AlipayConfigService;
import com.youjun.payment.alipay.vo.TradeVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;


/**
 * @author hupeng
 * @date 2020-05-13
 */
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "alipayConfig")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AlipayConfigServiceImpl implements AlipayConfigService {

    @Autowired
    AlipayConfig alipayConfig;

    @Override
    public String toPayAsPc(AlipayConfig alipay, TradeVo trade) throws Exception {

        if (alipay.getId() == null) {
            throw new ApiException("请先添加相应配置，再操作");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppId(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(), alipay.getPublicKey(), alipay.getSignType());

        // 创建API对应的request(电脑网页版)
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        // 订单完成后返回的页面和异步通知地址
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        // 填充订单参数
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + trade.getOutTradeNo() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + trade.getTotalAmount() + "," +
                "    \"subject\":\"" + trade.getSubject() + "\"," +
                "    \"body\":\"" + trade.getBody() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"" + alipay.getSysServiceProviderId() + "\"" +
                "    }" +
                "  }");//填充业务参数
        // 调用SDK生成表单, 通过GET方式，口可以获取url
        return alipayClient.pageExecute(request, "GET").getBody();

    }

    @Override
    public String toPayAsWeb(AlipayConfig alipay, TradeVo trade) throws Exception {
        if (alipay.getId() == null) {
            throw new ApiException("请先添加相应配置，再操作");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppId(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(), alipay.getPublicKey(), alipay.getSignType());

        double money = Double.parseDouble(trade.getTotalAmount());
        double maxMoney = 5000;
        if (money <= 0 || money >= maxMoney) {
            throw new ApiException("测试金额过大");
        }
        // 创建API对应的request(手机网页版)
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + trade.getOutTradeNo() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + trade.getTotalAmount() + "," +
                "    \"subject\":\"" + trade.getSubject() + "\"," +
                "    \"body\":\"" + trade.getBody() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"" + alipay.getSysServiceProviderId() + "\"" +
                "    }" +
                "  }");
        return alipayClient.pageExecute(request, "GET").getBody();
    }

    @Override
    public String toPayAsApp(AlipayConfig alipay, TradeVo trade) throws Exception {
        if (alipay.getId() == null) {
            throw new ApiException("请先添加相应配置，再操作");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppId(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(), alipay.getPublicKey(), alipay.getSignType());

        double money = Double.parseDouble(trade.getTotalAmount());
        double maxMoney = 5000;
        if (money <= 0 || money >= maxMoney) {
            throw new ApiException("测试金额过大");
        }
        // 创建API对应的request(手机网页版)
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        //BizModel和BizContent 二选一,都有则BizContent优先
        /* AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(trade.getOutTradeNo());
        model.setTotalAmount(trade.getTotalAmount());
        model.setSubject(trade.getSubject());
        model.setBody(trade.getBody());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);*/
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + trade.getOutTradeNo() + "\"," +
                "    \"product_code\":\"QUICK_MSECURITY_PAY\"," +
                "    \"total_amount\":" + trade.getTotalAmount() + "," +
                "    \"subject\":\"" + trade.getSubject() + "\"," +
                "    \"body\":\"" + trade.getBody() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"" + alipay.getSysServiceProviderId() + "\"" +
                "    }" +
                "  }");
        return alipayClient.sdkExecute(request).getBody();
    }

    @Override
    public AlipayConfig find() {
        return alipayConfig;
    }

    @Override
    public void update(AlipayConfig alipayConfig) {
        this.alipayConfig.setAppId(alipayConfig.getAppId());
        this.alipayConfig.setCharset(alipayConfig.getCharset());
        this.alipayConfig.setFormat(alipayConfig.getFormat());
        this.alipayConfig.setGatewayUrl(alipayConfig.getGatewayUrl());
        this.alipayConfig.setNotifyUrl(alipayConfig.getNotifyUrl());
        this.alipayConfig.setPrivateKey(alipayConfig.getPrivateKey());
        this.alipayConfig.setPublicKey(alipayConfig.getPublicKey());
        this.alipayConfig.setReturnUrl(alipayConfig.getReturnUrl());
        this.alipayConfig.setSignType(alipayConfig.getSignType());
        this.alipayConfig.setSysServiceProviderId(alipayConfig.getSysServiceProviderId());
    }

}
