package com.lyy.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    //商品数量
    private Integer productQuantity;

    private String productIcon;
}
