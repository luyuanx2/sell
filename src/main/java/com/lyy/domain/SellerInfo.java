package com.lyy.domain;

import javax.persistence.Id;

/**
 * Created by luyuanyuan on 2017/9/27.
 */
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}
