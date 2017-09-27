package com.lyy.service;

import com.lyy.domain.SellerInfo;

/**
 * Created by luyuanyuan on 2017/9/27.
 */
public interface SellerInfoService {

    SellerInfo findByOpenid(String openid);
}
