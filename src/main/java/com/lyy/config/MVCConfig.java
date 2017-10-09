/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 *
 */

package com.lyy.config;


import com.lyy.interceptor.CtxInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class MVCConfig extends WebMvcConfigurerAdapter {

    private final CtxInterceptor ctxInterceptor;

    @Autowired
    public MVCConfig(CtxInterceptor ctxInterceptor) {

        this.ctxInterceptor = ctxInterceptor;

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(ctxInterceptor)
                .addPathPatterns("/**/order/list");

//                .addPathPatterns("/**").excludePathPatterns("/goods/**", "/assets/**", "/alimama/**", "/manage/**"
//                , "/admin/**", "/wap/**", "/platform/**", "/passwordAuth", "", "/", "/test/**", "/public-platform/**"
//                , "/t/**", "/portal/**", "/goodInfoLink/**", "/weixin/sdk/config");
//        registry.addInterceptor(checkTokenInterceptor).addPathPatterns("/weChatGroup/**", "/task/**");
//        registry.addInterceptor(manageInterceptor).addPathPatterns("/manage/**").excludePathPatterns("/manage/",
//                "/manage", "/manage/login");
    }





}
