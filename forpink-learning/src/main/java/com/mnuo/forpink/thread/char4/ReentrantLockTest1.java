package com.mnuo.forpink.thread.char4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest1 {
	public static void main(String[] args) {
		Myservice3 myservice = new Myservice3();
		Thread t1 = new MyThread1(myservice);
		Thread t2 = new MyThread1(myservice);
		Thread t3 = new MyThread1(myservice);
		Thread t4 = new MyThread1(myservice);
		Thread t5 = new MyThread1(myservice);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}
class MyThread1 extends Thread{
	private Myservice3 myservice;
	public MyThread1(Myservice3 myservice){
		super();
		this.myservice = myservice;
	}
	@Override
	public void run() {
		myservice.testMethod();
	}
}
class Myservice3{
	private Lock lock = new ReentrantLock();
	
	public void testMethod(){
		lock.lock();
		for (int i = 0; i < 10; i++) {
			System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i+1)));
		}
		lock.unlock();
	}
}