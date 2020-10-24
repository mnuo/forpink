package com.mnuo.forpink.tcc.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import feign.Param;

/**
 * @author: peijiepang
 * @date 2019-11-12
 * @Description:
 */
@FeignClient(value = "fromaccount-service")
public interface RmOneApi {

  @PostMapping(value = "/order/create",consumes = MediaType.APPLICATION_JSON_VALUE)
  public String createOrder(@Param("order") String order);

}
