package com.mnuo.forpink.seata.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mnuo.forpink.seata.storage.entity.TStorage;

/**
 * <p>
 * 装车单 服务类
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
public interface StorageService extends IService<TStorage> {
	Object findAll();

	void decrease(Long productId, Integer count);
}
