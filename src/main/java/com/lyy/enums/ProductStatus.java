package com.lyy.enums;

import lombok.Getter;

/**商品状态
 * Created by 鲁源源 on 2017/9/14.
 */
@Getter
public enum ProductStatus implements CodeEnum {
    UP(0,"在架"),
    DOWN(1,"下架")
    ;

    private Integer code;

    private String message;

    ProductStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
