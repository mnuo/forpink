package com.mnuo.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mnuo.order.entity.TOrder;

/**
 * <p>
 * 装车单 服务类
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
public interface OrderService extends IService<TOrder> {
	Object findAll();

	void create(TOrder order);
}
