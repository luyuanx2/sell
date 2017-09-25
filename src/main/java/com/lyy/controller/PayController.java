package com.lyy.controller;

import com.lly835.bestpay.model.PayResponse;
import com.lyy.dto.OrderDTO;
import com.lyy.enums.ResultCode;
import com.lyy.exception.SellException;
import com.lyy.service.OrderService;
import com.lyy.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luyuanyuan on 2017/9/25.
 */
@Controller
@RequestMapping("/pay/")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;


    @GetMapping("/create")
    public ModelAndView create(@RequestParam String orderId,
                       String returnUrl){
        //查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultCode.ORDER_NOT_EXIST);
        }

        //发起支付
        //2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        Map<String,Object> map = new HashMap<>();
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
