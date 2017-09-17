package com.lyy.enums;

import lombok.Getter;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Getter
public enum ResultCode {

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不足"),
    ;
    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
