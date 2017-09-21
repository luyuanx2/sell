package com.lyy.controller;

import com.lyy.converter.OrderForm2OrderDTOConverter;
import com.lyy.dto.OrderDTO;
import com.lyy.enums.ResultCode;
import com.lyy.exception.SellException;
import com.lyy.form.OrderForm;
import com.lyy.service.BuyerService;
import com.lyy.service.OrderService;
import com.lyy.utils.ResultVOUtil;
import com.lyy.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private BuyerService buyerService;

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


    /**
     * 订单列表
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public ResultVo<List<OrderDTO>> list(String openid,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size){
        if(StringUtils.isBlank(openid)){
            log.error("【订单查询列表】微信号为空");
            throw new SellException(ResultCode.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<OrderDTO> orders = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orders.getContent());
    }

    /**
     *
     * @param openid
     * @param orderId
     * @return
     */
    @GetMapping("detail")
    public ResultVo<OrderDTO> detail(String openid,String orderId){
        if(StringUtils.isBlank(openid)  || StringUtils.isBlank(orderId)){
            log.error("【订单详情】参数错误");
            throw new SellException(ResultCode.PARAM_ERROR);
        }

        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);

        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("cancel")
    public ResultVo cancel(String openid,String orderId){
        if(StringUtils.isBlank(openid)  || StringUtils.isBlank(orderId)){
            log.error("【订单详情】参数错误");
            throw new SellException(ResultCode.PARAM_ERROR);
        }
        //todo 取消流程
        OrderDTO orderDTO = buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
