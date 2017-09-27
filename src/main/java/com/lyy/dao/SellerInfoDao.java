package com.lyy.dao;

import com.lyy.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luyuanyuan on 2017/9/27.
 */
public interface SellerInfoDao extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);
}
