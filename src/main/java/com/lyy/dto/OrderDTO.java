package com.lyy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lyy.domain.OrderDetail;
import com.lyy.enums.OrderStatus;
import com.lyy.enums.PayStatus;
import com.lyy.utils.EnumUtil;
import com.lyy.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatus getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatus.class);
    }

    @JsonIgnore
    public PayStatus getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatus.class);
    }
}
