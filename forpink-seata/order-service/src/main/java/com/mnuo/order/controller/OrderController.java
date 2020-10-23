package com.mnuo.order.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.order.entity.TOrder;
import com.mnuo.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 派车单 前端控制器
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/order")
@Api(tags = "1 order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/list")
    @ApiOperation(value = "1 list")
    @ApiImplicitParams({
    })
    public Object collect(){
        return orderService.findAll();
    }
	/**
     * 创建订单
     */
    @GetMapping("/create")
    public Map<String, String> create(TOrder order) {
    	 orderService.create(order);
    	 Map<String, String> map = new HashMap<>();
         map.put("code", "200");
         map.put("msg", "下单成功！");
         return map;
    }
}

