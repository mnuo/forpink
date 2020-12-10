package com.mnuo.forpink.thread;

public class Test2 {
	public static void main(String[] args) {
	//	Mythread3 thread = new Mythread3("A");
	//	Mythread3 thread1 = new Mythread3("B");
	//	Mythread3 thread2 = new Mythread3("C");
	//	thread.start();
	//	thread1.start();
	//	thread2.start();
		Mythread4 thread = new Mythread4();
		Thread thread0 = new Thread(thread, "A");
		Thread thread1 = new Thread(thread,"B");
		Thread thread2 = new Thread(thread,"C");
		Thread thread3 = new Thread(thread,"D");
		Thread thread4 = new Thread(thread,"E");
		Thread thread5 = new Thread(thread,"F");
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
	}

}

class Mythread3 extends Thread{
	Mythread3(String name){
		super();
		this.setName(name);
	}
	private int count = 5;
	@Override
	public void run() {
		super.run();
		while (count > 0) {
			count --;
			
			System.out.println("由 " + this.currentThread().getName()
	                    + " 计算，count=" + count);
		}
	}
}
class Mythread4 implements Runnable{
	
	private int count = 5;
	@Override
	public void run() {
			count --;
			System.out.println("由  计算，count="+count);
	}
}
class Mythread4_0 extends Thread{
	Mythread4_0(String name){
		super();
		this.setName(name);
	}
	private static int count = 5;
	@Override
	public void run() {
			count --;
			System.out.println("由 "+this.currentThread().getName()+" 计算，count="+count);
	}
}
