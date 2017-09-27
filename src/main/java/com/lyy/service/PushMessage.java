package com.lyy.service;

import com.lyy.dto.OrderDTO;

/**
 * 消息推送
 * Created by 鲁源源 on 2017/9/27.
 */
public interface PushMessage {

    void orderStatus(OrderDTO orderDTO);
}
