package com.mnuo.forpink.thread;

public class Test1 {
	public static void main(String[] args) throws InterruptedException {
//		System.out.println(Thread.currentThread().getName());
		
//		MyThread myThread = new MyThread();
//		myThread.start();
//		Thread.sleep(200);
//		System.out.println("运行结束!");
		
//		MyRunnable myRunnable = new MyRunnable();
//		Thread thread = new Thread(myRunnable);
//		thread.start();
//		System.out.println("运行结束");
		
//		MyRunnable2 myRunnable = new MyRunnable2();
//		Thread thread = new Thread(myRunnable);
//		thread.start();
//		System.out.println("运行结束");
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.println("i + j = " + (i+j));
				continue;
			}
			System.out.println("i = " + i);
		}
	}

}

//继承Thread
class MyThread extends Thread{
	@Override
	public void run() {
		super.run();
		System.out.println("thread");
	}
}
//实现Runnable
class MyRunnable implements Runnable{

	@Override
	public void run() {
		System.out.println("运行中");
		
	}
	
}

class MyRunnable2 extends MyRunnable implements Runnable{
	public void doSomething(){
		System.out.println("dosomething");
	}
	@Override
	public void run() {
		doSomething();
	}
}
