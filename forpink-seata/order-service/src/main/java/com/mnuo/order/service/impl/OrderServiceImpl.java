package com.mnuo.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mnuo.order.entity.TOrder;
import com.mnuo.order.mapper.OrderMapper;
import com.mnuo.order.service.AccountService;
import com.mnuo.order.service.OrderService;
import com.mnuo.order.service.StorageService;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 装车单 服务实现类
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
@Service
@Slf4j
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, TOrder> implements OrderService {
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	StorageService storageService;

	@Autowired
	AccountService accountService;
	@Override
	public Object findAll() {
		return super.list();
	}

	@Override
	@GlobalTransactional(name = "forpink-create-order",rollbackFor = Exception.class)
	public void create(TOrder order) {
		log.info("------->下单开始");
		// 本应用创建订单
		orderMapper.create(order);

		// 远程调用库存服务扣减库存
		log.info("------->order-service中扣减库存开始");
		storageService.decrease(order.getProductId(), order.getCount());
		log.info("------->order-service中扣减库存结束");

		// 远程调用账户服务扣减余额
		log.info("------->order-service中扣减余额开始");
		accountService.decrease(order.getUserId(), order.getMoney());
		log.info("------->order-service中扣减余额结束");

		// 修改订单状态为已完成
		log.info("------->order-service中修改订单状态开始");
		orderMapper.update(order.getUserId(), 0);
		log.info("------->order-service中修改订单状态结束");

		log.info("------->下单结束");
	}
}
