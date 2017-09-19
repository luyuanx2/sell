package com.lyy.controller;

import com.lyy.converter.OrderForm2OrderDTOConverter;
import com.lyy.dto.OrderDTO;
import com.lyy.enums.ResultCode;
import com.lyy.exception.SellException;
import com.lyy.form.OrderForm;
import com.lyy.service.OrderService;
import com.lyy.utils.ResultVOUtil;
import com.lyy.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 鲁源源 on 2017/9/19.
 */
@RestController
@RequestMapping("/buyer/order/")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确,orderForm={}",orderForm);
            throw new SellException(ResultCode.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDto = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空,");
            throw new SellException(ResultCode.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDto);

        Map<String, String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());


        return ResultVOUtil.success(map);
    }
}
