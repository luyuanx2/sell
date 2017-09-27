package com.lyy.service.impl;

import com.lyy.dao.SellerInfoDao;
import com.lyy.domain.SellerInfo;
import com.lyy.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luyuanyuan on 2017/9/27.
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoDao sellerInfoDao;
    @Override
    public SellerInfo findByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }
}
