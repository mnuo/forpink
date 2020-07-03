package com.mnuo.forpink.seckill.common.zk;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkLockUtil {
	private static String address="127.0.0.1:2181";
	
	private static CuratorFramework client;
	
	static{
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(address, retryPolicy);
		client.start();
	}
	/**
     * 私有的默认构造子，保证外界无法直接实例化
     */
	private ZkLockUtil(){
		
	}
	/**
	 * 类级的内部类,也就是静态的成员式内部类, 该内部的实例与外部类的实例
	 * 没有绑定关系, 而且只有被调用到才会装载,从而实现了延迟加载
	 * 针对一件商品实现,多件商品同事秒杀建议实现一个map
	 * @author mon
	 *
	 */
	private static class singletonHolder{
		/**
		 * 静态初始化器, 由JVM来保证线程安全
		 * 这里建议new一个
		 */
		private static InterProcessMutex mutex = new InterProcessMutex(client, "curator/lock");
	}
	public static InterProcessMutex getMutex(){
		return singletonHolder.mutex;
	}
	/**
	 * 获得锁
	 * @param time
	 * @param unit
	 * @return
	 */
	public static boolean acquire(long time, TimeUnit unit){
		try {
			return getMutex().acquire(time, unit);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void release(){
		try {
			getMutex().release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
