package com.lyy.service;

import com.lyy.dto.OrderDTO;

/**
 * Created by 鲁源源 on 2017/9/22.
 */
public interface BuyerService {

    OrderDTO findOrderOne(String openid,String orderId);

    OrderDTO cancelOrder(String openid,String orderId);
}
