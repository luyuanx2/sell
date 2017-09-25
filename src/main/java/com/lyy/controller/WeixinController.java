package com.lyy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 微信相关
 * Created by luyuanyuan on 2017/9/25.
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping
    public void auth(@RequestParam String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);
    }
}
