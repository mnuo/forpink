package com.mnuo.forpink.seata.storage.controller;


import java.util.HashMap;
import java.util.Map;

import com.mnuo.forpink.seata.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/storage")
@Api(tags = "1 storage")
public class StorageController {
	
	@Autowired
    StorageService storageService;
	
	@GetMapping("/list")
    @ApiOperation(value = "1 list")
    @ApiImplicitParams({
    })
    public Object collect(){
        return storageService.findAll();
    }
	/**
     * 扣减库存
     */
    @RequestMapping("/decrease")
    public Map<String,String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count){
    	storageService.decrease(productId, count);
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "扣减库存成功！");
        return map;
    }
}

