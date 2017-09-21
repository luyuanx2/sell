package com.lyy.service.impl;

import com.lyy.dto.OrderDTO;
import com.lyy.enums.ResultCode;
import com.lyy.exception.SellException;
import com.lyy.service.BuyerService;
import com.lyy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 鲁源源 on 2017/9/22.
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService{
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = getOrderDTO(openid, orderId);
        if (orderDTO == null) return null;
        return orderDTO;
    }

    private OrderDTO getOrderDTO(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单的openid不一致,openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultCode.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = getOrderDTO(openid, orderId);
        return orderService.cancel(orderDTO);
    }
}
