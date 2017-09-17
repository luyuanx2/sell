package com.lyy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Data
@AllArgsConstructor
public class CartDTO {

    private String productId;

    private Integer productQuantity;
}
