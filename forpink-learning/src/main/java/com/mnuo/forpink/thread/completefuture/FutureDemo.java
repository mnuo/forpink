package com.mnuo.forpink.thread.completefuture;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.druid.support.logging.Log;

public class FutureDemo {
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(10);
		try {

			// 结果集
			List<Integer> list = new ArrayList<>();
			List<Future<Integer>> futureList = new ArrayList<>();

			// 高速提交10个任务, 每个任务返回一个future入list
			for (int i = 0; i < 10; i++) {
				futureList.add(service.submit(new CallableTask(i)));
			}
			Long getResultStart = System.currentTimeMillis();
			System.out.println("结果集开始归集时间: " + new Date());

			while (futureList.size() > 0) {
				Iterator<Future<Integer>> iterator = futureList.iterator();
				while (iterator.hasNext()) {
					Future<Integer> future = iterator.next();
					if (future.isDone() && !future.isCancelled()) {
						// 获取结果集
						Integer i;
						try {
							i = future.get();
							System.out.println("任务i=" + i + "任务完成, 移除队列!" + new Date());
							list.add(i);
							iterator.remove();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							Thread.sleep(2);// 避免CPU高速运转, 休眠2ms
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			System.out.println("list=" + list);
			System.out.println("总耗时: " + (System.currentTimeMillis() - start) + ", 收集结果耗时: " + (System.currentTimeMillis() - getResultStart));
		} catch (Exception e) {
		} finally {
			service.shutdown();
		}

	}
}

class CallableTask implements Callable<Integer> {
	Integer i;

	CallableTask(Integer i) {
		this.i = i;
	}

	@Override
	public Integer call() throws Exception {
		if (i == 1) {
			Thread.sleep(3000);
		} else if (i == 5) {
			Thread.sleep(5000);
		} else {
			Thread.sleep(1000);
		}
		System.out.println(
				"task线程: " + Thread.currentThread().getName() + "任务i=" + i + ", 完成" + System.currentTimeMillis());
		return i;
	}

}
