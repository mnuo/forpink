package com.mnuo.order.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "storage-service")
@FeignClient(name = "storage-service", path = "/storage-service")
public interface StorageService {

    /**
     * 扣减库存
     */
    @GetMapping(value = "/storage/decrease")
    Map<String, String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
