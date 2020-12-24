package com.mnuo.forpink.thread.char3;

public class ThreadLocalTest {
	public static void main(String[] args) throws Exception {
		ThreadLocalThreadA a = new ThreadLocalThreadA();
		ThreadLocalThreadB b = new ThreadLocalThreadB();
		a.start();
		b.start();
		
		for (int i = 0; i < 10; i++) {
			Tools.t1.set("main " + (i+1));
			System.out.println("main get" + Tools.t1.get());
			int sleepValue = (int) (Math.random() * 1000);
			Thread.sleep(sleepValue);
		}
	}
}
class Tools{
	public static ThreadLocal<Object> t1 = new ThreadLocal<>();
}
class ThreadLocalThreadA extends Thread {
	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				Tools.t1.set("A" + (i+1));
				System.out.println("A get" + Tools.t1.get());
				int sleepValue = (int) (Math.random() * 1000);
				Thread.sleep(sleepValue);
			}
		} catch (Exception e) {
		}
	}
}
class ThreadLocalThreadB extends Thread {
	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				Tools.t1.set("B" + (i+1));
				System.out.println("B get" + Tools.t1.get());
				int sleepValue = (int) (Math.random() * 1000);
				Thread.sleep(sleepValue);
			}
		} catch (Exception e) {
		}
	}
}
