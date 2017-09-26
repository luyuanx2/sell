package com.lyy.enums;

import lombok.Getter;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Getter
public enum PayStatus implements CodeEnum {

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功");
    private Integer code;

    private String message;

    PayStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
