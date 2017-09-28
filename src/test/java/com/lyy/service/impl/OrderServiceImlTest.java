package com.lyy.service.impl;

import com.lyy.BaseTest;
import com.lyy.domain.OrderDetail;
import com.lyy.dto.OrderDTO;
import com.lyy.service.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyuanyuan on 2017/9/18.
 */
public class OrderServiceImlTest extends BaseTest{

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        //创建订单
        OrderDTO orderDto = new OrderDTO();
        orderDto.setBuyerName("lyy");
        orderDto.setBuyerPhone("18868822111");
        orderDto.setBuyerAddress("慕课网总部");
        orderDto.setBuyerOpenid("ew3euwhd7sjw9diwkq");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("111");
        orderDetail.setProductQuantity(2);
        orderDetailList.add(orderDetail);
        orderDto.setOrderDetailList(orderDetailList);

        OrderDTO orderDTO = orderService.create(orderDto);

        System.out.println(orderDTO);

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findList() throws Exception {

        PageRequest pageRequest = new PageRequest(1,10);
        Page<OrderDTO> orderDTOPage = orderService.findList("ew3euwhd7sjw9diwkq", pageRequest);

        System.out.println(orderDTOPage);
    }

    @Test
    public void cancel() throws Exception {

    }

    @Test
    public void finish() throws Exception {

    }

    @Test
    public void paid() throws Exception {

    }

}