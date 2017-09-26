package com.lyy.controller;

import com.lyy.dto.OrderDTO;
import com.lyy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luyuanyuan on 2017/9/26.
 */
@Controller
@RequestMapping("/seller/order/")
@Slf4j
public class SellerController {

    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size){

        PageRequest pageRequest = new PageRequest(page - 1,size);

        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);

        Map<String,Object> map = new HashMap<>();
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

}
