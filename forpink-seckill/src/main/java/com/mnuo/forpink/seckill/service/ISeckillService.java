package com.mnuo.forpink.seckill.service;

import com.mnuo.forpink.seckill.common.Result;

public interface ISeckillService {

	void deleteSeckill(long seckillId);

	Result startSeckill(long killId, long userId);

	Long getSeckillCount(long seckillId);

	Result startSeckillLock(long killId, long userId);
	
	Result startSeckillAoplock(long killId, long userId);

	Result startSeckillDBPCC_ONE(long killId, long userId);

	Result startSeckilDBPCC_TWO(long killId, long userId);

	Result startSeckilDBOCC(long killId, long userId, int i);
}
