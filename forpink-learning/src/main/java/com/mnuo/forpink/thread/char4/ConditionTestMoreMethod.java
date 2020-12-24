package com.mnuo.forpink.thread.char4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTestMoreMethod {

}
class MyService1{
	private Lock lock = new ReentrantLock();
	public void method1(){
		try {
			lock.lock();
			System.out.println("menthod1 begin ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
			Thread.sleep(5000);
			System.out.println("menthod1 end ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.unlock();
		}
	}
	public void method2(){
		try {
			lock.lock();
			System.out.println("menthod2 begin ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
			Thread.sleep(5000);
			System.out.println("menthod2 end ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.unlock();
		}
	}
}
class MyThread2 extends Thread{
	private MyService1 myService1;
	MyThread2(MyService1 myService1){
		this.myService1 = myService1;
	}
	@Override
	public void run() {
		myService1.method1();
	}
}