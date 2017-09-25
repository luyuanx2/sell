package com.lyy.service.impl;

import com.lyy.dao.OrderDetailDao;
import com.lyy.dao.OrderMasterDao;
import com.lyy.domain.OrderDetail;
import com.lyy.domain.OrderMaster;
import com.lyy.domain.ProductInfo;
import com.lyy.dto.CartDTO;
import com.lyy.dto.OrderDTO;
import com.lyy.enums.OrderStatus;
import com.lyy.enums.PayStatus;
import com.lyy.enums.ResultCode;
import com.lyy.exception.SellException;
import com.lyy.service.OrderService;
import com.lyy.service.PayService;
import com.lyy.service.ProductService;
import com.lyy.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Service
@Transactional
@Slf4j
public class OrderServiceIml implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private PayService payService;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        //List<CartDTO> cartDTOList =  new ArrayList<>();
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                //商品不存在
                throw new SellException(ResultCode.PRODUCT_NOT_EXIST);
            }

            //计算总价
            orderAmount = orderAmount.add(productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity())));

            //订单详情入库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetailDao.save(orderDetail);

            //cartDTOList.add(new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity()));
        }
        
        //订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatus.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatus.NEW.getCode());
        orderMasterDao.save(orderMaster);

        //扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(x -> new CartDTO(x.getProductId(), x.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultCode.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ResultCode.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOs = page.getContent().stream().map(x -> {
                    OrderDTO orderDTO = new OrderDTO();
                    BeanUtils.copyProperties(x, orderDTO);
                    return orderDTO;
                }
        ).collect(Collectors.toList());

        Page<OrderDTO> page1 = new PageImpl<>(orderDTOs,pageable,page.getTotalElements());
        return page1;
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        if(!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            log.error("【取消订单】订单状态不正确,orderId={}, orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultCode.ORDER_STATUS_ERROR);
        }
        //OrderMaster orderMaster = getOrderMaster(orderDTO);
        orderDTO.setOrderStatus(OrderStatus.CANCEL.getCode());

        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultCode.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情,orderDTO={}",orderDTO);
        }

        List<CartDTO> collect = orderDTO.getOrderDetailList().stream()
                .map(x -> new CartDTO(x.getProductId(), x.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(collect);

        //已经支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatus.SUCCESS)){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     * 订单完成
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {

        if(!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            log.error("【完成订单】订单状态不正确,orderId={}, orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultCode.ORDER_STATUS_ERROR);
        }
        
        orderDTO.setOrderStatus(OrderStatus.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster update = orderMasterDao.save(orderMaster);
        if(update == null){
            log.error("【完成订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultCode.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }


    /**
     * 订单支付
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //只有是新订单才能完成支付
        if(!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            log.error("【订单支付】订单状态不正确,orderId={}, orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultCode.ORDER_STATUS_ERROR);
        }

        //只有是未支付的的订单才能支付
        if(!orderDTO.getPayStatus().equals(PayStatus.WAIT.getCode())){
            log.error("【订单支付】订单支付状态不正确,orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultCode.ORDER_PAY_ERROR);
        }
        orderDTO.setPayStatus(PayStatus.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster update = orderMasterDao.save(orderMaster);
        if(update == null){
            log.error("【支付订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultCode.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

//    private OrderMaster getOrderMaster(OrderDTO orderDTO) {
//        OrderMaster orderMaster = orderMasterDao.findOne(orderDTO.getOrderId());
//        if(orderMaster == null){
//            //订单不存在
//            throw new SellException(ResultCode.ORDER_NOT_EXIST);
//        }
//        return orderMaster;
//    }
}
