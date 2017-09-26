package com.lyy.enums;

import lombok.Getter;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Getter
public enum OrderStatus implements CodeEnum {

    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消");

    private Integer code;

    private String message;

    OrderStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
