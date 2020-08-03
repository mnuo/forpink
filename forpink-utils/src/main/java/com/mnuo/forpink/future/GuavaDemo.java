package com.mnuo.forpink.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class GuavaDemo {
	static final int Sleep_time = 1000;
	
	public static String getCurThreadName(){
		return Thread.currentThread().getName();
	}
	
	static class MainJob implements Runnable{
		boolean waterOk = false;
		boolean cupOk = false;
		int gap = Sleep_time / 10;
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(gap);
					System.out.println("读书中....");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(waterOk && cupOk){
					drinkTea(waterOk, cupOk);
				}
			}
			
		}
		public void drinkTea(boolean waterOk2, boolean cupOk2) {
			 if (waterOk2&&cupOk2) {
	                System.out.println("泡茶喝，茶喝完");
	                this.waterOk = false;
	                this.gap = Sleep_time * 100;
	            } else if (!waterOk2) {
	            	System.out.println("烧水失败，没有茶喝了");
	            } else if (!cupOk2) {
	            	System.out.println("杯子洗不了，没有茶喝了");
	            }
			
		}
		
	}
	
	static class HotWater implements Callable<Boolean>{
		@Override
		public Boolean call() throws Exception {
			System.err.println(Thread.currentThread().getName()+": 烧水开始");
			try {
				System.out.println("洗好水壶");
				System.out.println("灌上凉水");
				System.out.println("开火");
				Thread.sleep(Sleep_time);
				System.out.println("烧水完成");
			} catch (InterruptedException e) {
				System.out.println("烧水异常");
				e.printStackTrace();
				return false;
			}
			System.out.println("运行结束");
			return true;
		}
	}
	static class WashTask implements Callable<Boolean> {
			
		@Override
		public Boolean call() throws Exception {
			System.err.println(Thread.currentThread().getName()+": 清洗开始");
			try {
				System.out.println("洗壶");
				System.out.println("洗杯");
				System.out.println("那叶子");
				Thread.sleep(Sleep_time);
				System.out.println("洗完成");
			} catch (InterruptedException e) {
				System.out.println("洗异常");
				e.printStackTrace();
				return false;
			}
			System.out.println("运行结束");
			return true;
		}
	}
	public static void main(String[] args) {
		MainJob mainjob = new MainJob();
		Thread mt = new Thread(mainjob, "my-main-thread");
		mt.start();
		
		HotWater hw = new HotWater();
		WashTask wt = new WashTask();
		//创建Java线程池
		ExecutorService jpool = Executors.newFixedThreadPool(100);
		//包装java线程池, 构造Guava线程池
		ListeningExecutorService gpool = MoreExecutors.listeningDecorator(jpool);
		//提交Hotwater实例
		ListenableFuture<Boolean> hotfuture = gpool.submit(hw);
		//绑定异步回调, 烧水完成后, 把喝水任务的waterok设置为TRUE
		Futures.addCallback(hotfuture, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					mainjob.waterOk = true;
				}
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("停电了, 烧水失败了");
			}
		});
		
		ListenableFuture<Boolean> waterfuture = gpool.submit(wt);
		Futures.addCallback(waterfuture, new FutureCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if(result){
					mainjob.waterOk = true;
				}
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("停水了, 被子背摔了");
			}
		});
		
		
		
	}
}
