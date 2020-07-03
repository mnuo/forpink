package com.mnuo.forpink.seckill.service;

import com.mnuo.forpink.seckill.common.Result;

public interface IProxSeckillService {

	Result seckillLockOutOfTransaction(long seckillId, long userId);

}
