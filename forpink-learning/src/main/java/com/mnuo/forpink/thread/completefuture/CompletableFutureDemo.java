package com.mnuo.forpink.thread.completefuture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public class CompletableFutureDemo {
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		List<String> list = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		//定长10线程
		List<CompletableFuture<String>> futures = new ArrayList<>();
		final List<Integer> taskList = Lists.newArrayList(2,1,3,4,5,6,7,8,9,10);
		ExecutorService service = Executors.newFixedThreadPool(10);
		try {
			for (int i = 0; i < 10; i++) {
				final int j = i;
				//异步执行
				CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> calc(taskList.get(j)), service)
						.thenApply(e -> Integer.toString(e))//Integer转换字符串    thenAccept只接受不返回不影响结果
						.whenComplete((v, e)->{//如需获取任务完成先后顺序，此处代码即可
							 System.out.println("任务"+v+"完成!result="+v+"，异常 e="+e+","+new Date());
							 list2.add(v);
						});
				futures.add(future);
			}
		//流式获取结果：此处是根据任务添加顺序获取的结果
		list = sequence(futures).get();
		
		
		System.out.println("list=" + list2);
		System.out.println("总耗时: " + (System.currentTimeMillis() - start));
	
		} catch (Exception e) {
		} finally {
			service.shutdown();
		}
		
	}
	
	private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
		//构造一个空的CompletableFuture, 子任务数为入参任务list size
		CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		//2, 流式 (总任务完成后, 每个子任务join取结果集, 后转换为list)
		return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
	}

	public static <T> CompletableFuture<List<T>> sequence(Stream<CompletableFuture<T>> futures) {
		List<CompletableFuture<T>> futureList = futures.filter(f -> f != null).collect(Collectors.toList());
		return sequence(futureList);
	}
	public static Integer calc(Integer i){
		try {
			if (i == 1) {
				Thread.sleep(3000);
			} else if (i == 5) {
				Thread.sleep(5000);
			} else {
				Thread.sleep(1000);
			}
			System.out.println(
					"task线程: " + Thread.currentThread().getName() + "任务i=" + i + ", 完成" + System.currentTimeMillis());
		} catch (Exception e) {
		}
		return i;
	}
	
}
