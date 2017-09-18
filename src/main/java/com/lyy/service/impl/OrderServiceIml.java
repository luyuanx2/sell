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
import com.lyy.service.ProductService;
import com.lyy.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
@Service
@Transactional
public class OrderServiceIml implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

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
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
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
        OrderDTO orderDTO = new OrderDTO();
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        BeanUtils.copyProperties(orderMaster,orderDTO);
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOs = page.getContent().stream().map(x -> {
                    List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(x.getOrderId());
                    OrderDTO orderDTO = new OrderDTO();
                    BeanUtils.copyProperties(x, orderDTO);
                    orderDTO.setOrderDetailList(orderDetails);
                    return orderDTO;
                }
        ).collect(Collectors.toList());
        return null;
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = getOrderMaster(orderDTO);
        orderMaster.setOrderStatus(OrderStatus.CANCEL.getCode());
        orderMasterDao.save(orderMaster);
        return orderDTO;
    }

    /**
     * 订单完成
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = getOrderMaster(orderDTO);
        orderMaster.setOrderStatus(OrderStatus.FINISHED.getCode());
        orderMasterDao.save(orderMaster);
        return orderDTO;
    }


    /**
     * 订单支付
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    private OrderMaster getOrderMaster(OrderDTO orderDTO) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderDTO.getOrderId());
        if(orderMaster == null){
            //订单不存在
            throw new SellException(ResultCode.ORDER_NOT_EXIST);
        }
        return orderMaster;
    }
}
