package com.lyy.exception;

import com.lyy.enums.ResultCode;
import lombok.Getter;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Getter
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
