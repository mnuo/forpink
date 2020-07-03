package com.mnuo.forpink.seckill.controller;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mnuo.forpink.seckill.common.Result;
import com.mnuo.forpink.seckill.service.ISeckillDistributedService;
import com.mnuo.forpink.seckill.service.ISeckillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags ="秒杀")
@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {
	private static int corePoolSize = Runtime.getRuntime().availableProcessors();
	//创建线程池  调整队列数 拒绝服务
	private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(10000));
	
	@Autowired
	private ISeckillService seckillService;
	
	@Autowired
	private ISeckillDistributedService seckillDistributedService;
	
	@ApiOperation(value="秒杀一(最low实现), 线程共享没有加锁")
	@PostMapping("/start")
	public Result start(long seckillId){
		int skillNum = 10;
		final CountDownLatch latch = new CountDownLatch(skillNum);//N个购买者
		seckillService.deleteSeckill(seckillId);
		
		final long killId = seckillId;
		log.info("开始miaosha(1),出现超卖现象.");
		
		/**
		 * 开启新线程之前, 将requestAttribute对象设置为子线程共享
		 * 这里仅仅是测试, 否则IPUtils获取不到request对象
		 * 用到限流的测试用例, 都需要加一下两行代码
		 */
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		RequestContextHolder.setRequestAttributes(attributes, true);
		
		for(int i = 0; i <skillNum; i ++){
			final long userId = i;
			Runnable task = ()->{
				/**
				 * 如果抛异常, 会影响结果
				 */
				try {
					Result result = seckillService.startSeckill(killId, userId);
					 if(result!=null){
                        log.info("用户:{}{}",userId,result.get("msg"));
                    }else{
                        log.info("用户:{}{}",userId,"哎呦喂，人也太多了，请稍后！");
                    }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				latch.countDown();
			};
			executor.execute(task);
		}
		try {
			latch.await();// 等待所有人任务结束
			Long  seckillCount = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品",seckillCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}
	@ApiOperation(value="秒杀二(程序锁)")
	@PostMapping("/startLock")
	public Result startLock(long seckillId){
		int skillNum = 1000;
		final CountDownLatch latch = new CountDownLatch(skillNum);
		seckillService.deleteSeckill(seckillId);
		final long killId = seckillId;
		for (int i = 0; i < skillNum; i++) {
			final long userId = i;
			
			Runnable task = () -> {
				Result result = seckillService.startSeckillLock(killId, userId);
				log.info("用户:{} {}", userId, result.get("msg"));
				latch.countDown();
			};
			executor.execute(task);
		}
		try {
			latch.await();
			Long count = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品", count);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok("秒杀完成");
	}
	@ApiOperation(value="秒杀三(AOP程序锁)")
	@PostMapping("/startAopLock")
	public Result startAopLock(long seckillId){
		int skillNum = 1000;
		final CountDownLatch latch = new CountDownLatch(skillNum);//N个购买者
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀三(正常)");
		for(int i=0;i<1000;i++){
			final long userId = i;
			Runnable task = () -> {
				Result result = seckillService.startSeckillAoplock(killId, userId);
				log.info("用户:{}{}",userId,result.get("msg"));
				latch.countDown();
			};
			executor.execute(task);
		}
		try {
			latch.await();// 等待所有人任务结束
			Long  seckillCount = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品",seckillCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}
	@ApiOperation(value="秒杀四(数据库悲观锁)")
	@PostMapping("/startDBPCC_ONE")
	public Result startDBPCC_ONE(long seckillId){
		int skillNum = 1000;
		final CountDownLatch latch = new CountDownLatch(skillNum);//N个购买者
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀四(正常)");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		RequestContextHolder.setRequestAttributes(attributes, true);
		
		for(int i=0;i<1000;i++){
			final long userId = i;
			Runnable task = () -> {
				try {
					Result result = seckillService.startSeckillDBPCC_ONE(killId, userId);
					log.info("用户:{}{}",userId,result.get("msg"));
					latch.countDown();
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			};
			executor.execute(task);
		}
		try {
			latch.await();// 等待所有人任务结束
			Long  seckillCount = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品",seckillCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}
	@ApiOperation(value="秒杀五(数据库悲观锁)")
	@PostMapping("/startDPCC_TWO")
	public Result startDPCC_TWO(long seckillId){
		int skillNum = 1000;
		final CountDownLatch latch = new CountDownLatch(skillNum);//N个购买者
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀五(正常、数据库锁最优实现)");
		for(int i=0;i<1000;i++){
			final long userId = i;
			Runnable task = () -> {
				Result result = seckillService.startSeckilDBPCC_TWO(killId, userId);
				log.info("用户:{}{}",userId,result.get("msg"));
				latch.countDown();
			};
			executor.execute(task);
		}
		try {
			latch.await();// 等待所有人任务结束
			Long  seckillCount = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品",seckillCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}
	@ApiOperation(value="秒杀六(数据库乐观锁)")
	@PostMapping("/startDBOCC")
	public Result startDBOCC(long seckillId){
		int skillNum = 1000;
		final CountDownLatch latch = new CountDownLatch(skillNum);//N个购买者
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀六(正常、数据库锁最优实现)");
		for(int i=0;i<1000;i++){
			final long userId = i;
			Runnable task = () -> {
				//这里使用的乐观锁、可以自定义抢购数量、如果配置的抢购人数比较少、比如120:100(人数:商品) 会出现少买的情况
				//用户同时进入会出现更新失败的情况
				Result result = seckillService.startSeckilDBOCC(killId, userId,1);
				log.info("用户:{}{}",userId,result.get("msg"));
				latch.countDown();
			};
			executor.execute(task);
		}
		try {
			latch.await();// 等待所有人任务结束
			Long  seckillCount = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品",seckillCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}
//	@ApiOperation(value="秒杀柒(进程内队列)")
//	@PostMapping("/startQueue")
//	public Result startQueue(long seckillId){
//		seckillService.deleteSeckill(seckillId);
//		final long killId =  seckillId;
//		LOGGER.info("开始秒杀柒(正常)");
//		for(int i=0;i<1000;i++){
//			final long userId = i;
//			Runnable task = () -> {
//				SuccessKilled kill = new SuccessKilled();
//				kill.setSeckillId(killId);
//				kill.setUserId(userId);
//				try {
//					Boolean flag = SeckillQueue.getMailQueue().produce(kill);
//					if(flag){
//						LOGGER.info("用户:{}{}",kill.getUserId(),"秒杀成功");
//					}else{
//						LOGGER.info("用户:{}{}",userId,"秒杀失败");
//					}
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//					LOGGER.info("用户:{}{}",userId,"秒杀失败");
//				}
//			};
//			executor.execute(task);
//		}
//		try {
//			Thread.sleep(10000);
//			Long  seckillCount = seckillService.getSeckillCount(seckillId);
//			LOGGER.info("一共秒杀出{}件商品",seckillCount);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return Result.ok();
//	}
//	@ApiOperation(value="秒杀柒(Disruptor队列)")
//	@PostMapping("/startDisruptorQueue")
//	public Result startDisruptorQueue(long seckillId){
//		seckillService.deleteSeckill(seckillId);
//		final long killId =  seckillId;
//		LOGGER.info("开始秒杀八(正常)");
//		for(int i=0;i<1000;i++){
//			final long userId = i;
//			Runnable task = () -> {
//				SeckillEvent kill = new SeckillEvent();
//				kill.setSeckillId(killId);
//				kill.setUserId(userId);
//				DisruptorUtil.producer(kill);
//			};
//			executor.execute(task);
//		}
//		try {
//			Thread.sleep(10000);
//			Long  seckillCount = seckillService.getSeckillCount(seckillId);
//			LOGGER.info("一共秒杀出{}件商品",seckillCount);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return Result.ok();
//	}
	
	@ApiOperation(value="秒杀一(Rediss分布式锁)")
	@PostMapping("/startRedisLock")
	public Result startRedisLock(long seckillId){
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("分布式开始秒杀一");
		for(int i=0;i<10000;i++){
			final long userId = i;
			Runnable task = () -> {
				Result result = seckillDistributedService.startSeckilRedisLock(killId, userId);
				log.info("用户:{}{}",userId,result.get("msg"));
			};
			executor.execute(task);
		}
		try {
			Thread.sleep(1500);
			Long  seckillCount = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品",seckillCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}
	@ApiOperation(value="秒杀二(zookeeper分布式锁)",nickname="科帮网")
	@PostMapping("/startZkLock")
	public Result startZkLock(long seckillId){
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀二");
		for(int i=0;i<10000;i++){
			final long userId = i;
			Runnable task = () -> {
				Result result = seckillDistributedService.startSeckilZksLock(killId, userId);
				log.info("用户:{}{}",userId,result.get("msg"));
			};
			executor.execute(task);
		}
		try {
			Thread.sleep(10000);
			Long  seckillCount = seckillService.getSeckillCount(seckillId);
			log.info("一共秒杀出{}件商品",seckillCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}
}
