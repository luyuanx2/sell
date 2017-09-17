package com.lyy.domain;

import com.lyy.enums.OrderStatus;
import com.lyy.enums.PayStatus;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    //买家微信号
    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatus.NEW.getCode();

    //支付状态
    private Integer payStatus = PayStatus.WAIT.getCode();

    private Date createTime;

    private Date updateTime;


}
