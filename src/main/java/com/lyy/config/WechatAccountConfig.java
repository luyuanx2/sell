package com.lyy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信账号相关配置
 * Created by luyuanyuan on 2017/9/25.
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众平台
     */
    private String mpAppId;
    private String mpAppSecret;

    /**
     * 开放平台
     */
    private String openAppId;
    private String openAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信支付回调地址
     */
    private String notifyUrl;

    private Map<String,String> templateId;
}
