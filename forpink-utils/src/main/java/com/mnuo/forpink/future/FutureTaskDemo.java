package com.mnuo.forpink.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
	static final int Sleep_time = 1000;
	
	static class HotWaterTask implements Callable<Boolean>{
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
	static void drinkTea(boolean hotwater, boolean wash){
		if(hotwater && wash){
			System.out.println("泡茶喝");
		}else if(!hotwater){
			System.out.println("烧水失败了");
		}else if(!wash){
			System.out.println("清洗失败了");
		}
	}
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName()+"主线程开始");
		Callable<Boolean> hw = new HotWaterTask();
		FutureTask<Boolean> hf = new FutureTask<>(hw);
		Thread ht = new Thread(hf, "烧水-线程");
		Callable<Boolean> ww = new WashTask();
		FutureTask<Boolean> wf = new FutureTask<>(ww);
		Thread wt = new Thread(wf,"清洗-线程");
		ht.start();
		wt.start();
		try {
			Boolean hresult = hf.get();
			Boolean wresult = wf.get();
			drinkTea(hresult, wresult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
