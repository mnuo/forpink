package com.mnuo.forpink.seata.storage.service.impl;

import com.mnuo.forpink.seata.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mnuo.forpink.seata.storage.entity.TStorage;
import com.mnuo.forpink.seata.storage.mapper.StorageMapper;

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
public class StorageServiceImpl extends ServiceImpl<StorageMapper, TStorage> implements StorageService {
	@Autowired
	StorageMapper storageMapper;

	@Override
	public Object findAll() {
		return super.list();
	}

	@Override
	public void decrease(Long productId, Integer count) {
		 log.info("------->storage-service中扣减库存开始");
		 storageMapper.decrease(productId,count);
	     log.info("------->storage-service中扣减库存结束");
	}
}
