package com.lyy.vo;

import lombok.Data;

/**
 * Created by 鲁源源 on 2017/9/15.
 */
@Data
public class ResultVo<T> {

    private Integer code;

    private String msg;

    private T data;
}
