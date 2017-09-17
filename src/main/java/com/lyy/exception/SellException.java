package com.lyy.exception;

import com.lyy.enums.ResultCode;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}
