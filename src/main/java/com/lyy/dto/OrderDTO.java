package com.lyy.dto;

import com.lyy.domain.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Data
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    //买家微信号
    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    //支付状态
    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
