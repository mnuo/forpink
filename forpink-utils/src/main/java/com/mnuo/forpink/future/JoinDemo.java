package com.mnuo.forpink.future;

public class JoinDemo {
	static final int Sleep_time = 1000;
	
	static class HotWater extends Thread{
		public HotWater(){
			super("hotWater-Thread");
		}
		@Override
		public void run() {
			try {
				System.out.println("洗好水壶");
				System.out.println("灌上凉水");
				System.out.println("开火");
				Thread.sleep(Sleep_time);
				System.out.println("烧水完成");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("运行结束");
		}
	}
	static class WashThread extends Thread{
		public WashThread(){
			super("wash-Thread");
		}
		@Override
		public void run() {
			try {
				System.out.println("洗壶");
				System.out.println("洗杯");
				System.out.println("那叶子");
				Thread.sleep(Sleep_time);
				System.out.println("洗完成");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("运行结束");
		}
	}
	public static void main(String[] args) {
		 Thread wash = new WashThread();
		 Thread hot = new HotWater();
		 hot.start();
		 wash.start();
		 
         try {
        	 // 合并烧水-线程
			hot.join();
			// 合并清洗-线程
			wash.join();
			Thread.currentThread().setName("主线程");
			System.out.println("泡茶喝");
		} catch (InterruptedException e) {
			System.err.println("合并出错");
			e.printStackTrace();
		}
         System.err.println(Thread.currentThread().getName() + "运行结束");
	}
}
