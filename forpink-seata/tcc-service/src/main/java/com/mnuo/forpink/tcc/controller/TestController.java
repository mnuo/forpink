package com.mnuo.forpink.tcc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.tcc.service.RmOneApi;
import com.mnuo.forpink.tcc.service.RmTwoApi;

import io.seata.spring.annotation.GlobalTransactional;

/**
 * @author: peijiepang
 * @date 2019-11-12
 * @Description:
 */
@RestController
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

  @Autowired
  private RmOneApi rmOneApi;

  @Autowired
  private RmTwoApi rmTwoApi;

  @GlobalTransactional
  @PostMapping("/tm/purchase")
  public String test(HttpServletRequest request,@RequestBody String orderDTO){
      String orderNo = Long.valueOf(RandomUtils.nextLong()).toString();
//      orderDTO.setOrderNo(orderNo);
      String result = rmOneApi.createOrder(orderDTO);
      LOGGER.info("create order {}",result);
      String type = request.getHeader("type");
      if(StringUtils.isNotEmpty(type) && type.equalsIgnoreCase("hot")){
          //更新操作-热点数据测试
//          boolean bol = rmTwoApi.reduceStorage(orderDTO.getProductId(),orderDTO.getCount());
//          if(!bol){
//              throw new RuntimeException("库存扣减失败");
//          }
      }else{
//          rmTwoApi.increaseStorage();
      }
      return "ok";
  }

}
