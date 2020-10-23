package com.mnuo.forpink.jvm;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JvmTest {
	private static int corePoolSize = Runtime.getRuntime().availableProcessors();
	private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(10000));
	public static void main(String[] args) {
//		final ThreadNotSafe count = new ThreadNotSafe();
//		for (int i = 0; i < 20; i++) {
//			Thread t = new Thread(new myInterface(count));
//			t.start();
//		}
//		for (int i = 0; i < 10; i++) {
//			Runnable task = ()->{
//				count.inc();
//			};
//			executor.execute(task);
//		}
		Hello hello = new Hello();
		for (int i = 0; i < 10; i++) {
			Runnable task = ()->{
				hello.helloA();
				hello.helloB();
//				System.out.println(count.getCount());
			};
			executor.execute(task);
		}

		executor.shutdown();
	}
}
class ThreadNotSafe{
	private Long value=0l;
	public Long getCount(){
		return value;
	}
	public synchronized void inc(){
		++value;
		System.out.println(value);
	}
}

class myInterface implements Runnable{
	ThreadNotSafe count;
	public myInterface(ThreadNotSafe count) {
		this.count = count;
	}
	@Override
	public void run() {
		count.inc();
		System.out.println(count.getCount());
	}
}


class Hello{
	public synchronized void helloA(){
		System.out.println("helloA");
	}
	
	public synchronized void helloB(){
		System.out.println("helloB");
		helloA();
	}
}
