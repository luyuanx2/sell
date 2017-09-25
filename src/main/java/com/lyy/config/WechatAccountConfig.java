package com.lyy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by luyuanyuan on 2017/9/25.
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    private String mpAppId;
    private String mpAppSecret;
}
