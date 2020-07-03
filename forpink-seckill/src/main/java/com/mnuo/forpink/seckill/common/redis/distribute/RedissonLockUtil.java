package com.mnuo.forpink.seckill.common.redis.distribute;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedissonLockUtil {
	private static RedissonClient redissonClient;
	
	public void setRedissonClient(RedissonClient locker){
		redissonClient = locker;
	}
	/**
	 * 加锁
	 */
	public static RLock lock(String lockKey){
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock();
		return lock;
	}
	/**
	 * 释放锁
	 */
	public static void unLock(String lockKey){
		RLock lock = redissonClient.getLock(lockKey);
		lock.unlock();
	}
	/**
	 * 释放锁
	 */
	public static void unLock(RLock lock){
		lock.unlock();
	}
	
	/**
	 * 带超时的锁
	 */
	public static RLock lock(String lockKey, int timeout){
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(timeout, TimeUnit.SECONDS);
		return lock;
	}
	/**
	 * 带超时的锁
	 */
	public static RLock lock(String lockKey, TimeUnit unit, int timeout){
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(timeout, unit);
		return lock;
	}
	
	/**
	 * 尝试获取锁
	 */
	public static boolean tryLock(String lockKey, int waitTime, int leaseTime){
		log.info("redissonClient" + redissonClient);
		RLock lock = redissonClient.getLock(lockKey);
		try {
			return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			return false;
		}
	}
	/**
	 * 尝试获取锁
	 */
	public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime){
		RLock lock = redissonClient.getLock(lockKey);
		try {
			return lock.tryLock(waitTime, leaseTime, unit);
		} catch (InterruptedException e) {
			return false;
		}
	}
	/**
	 * 初始化红包数量
	 */
	public void initCount(String key, int count){
		RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
		mapCache.putIfAbsent(key, count);
	}
	/**
	 * 递增
	 * @param key
	 * @param delta
	 * @return
	 */
	public int incr(String key, int delta){
		RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
		if(delta <= 0){
			throw new RuntimeException("递增因子必须大于0");
		}
		return mapCache.addAndGet(key, delta);
	}
	/**
	 * 递减
	 * @param key
	 * @param delta
	 * @return
	 */
	public int decr(String key, int delta){
		RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
		if(delta <= 0){
			throw new RuntimeException("递减因子必须大于0");
		}
		return mapCache.addAndGet(key, -delta);
	}
	
}
