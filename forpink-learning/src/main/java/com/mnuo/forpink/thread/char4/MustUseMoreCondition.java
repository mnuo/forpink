package com.mnuo.forpink.thread.char4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MustUseMoreCondition {

}
class Myservice2{
	private Lock lock = new ReentrantLock();
	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();
	
	public void awaitA(){
		try {
			
			System.out.println("menthod2 begin ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
			conditionA.await();
			System.out.println("menthod2 end ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
		} catch (Exception e) {
	
		} finally {
			lock.unlock();
		}
	}
	public void awaitB(){
		try {
			
			System.out.println("menthod2 begin ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
			conditionB.await();
			System.out.println("menthod2 end ThreadName=" + Thread.currentThread().getName() + " ,time=" + System.currentTimeMillis() );
		} catch (Exception e) {
			
		} finally {
			lock.unlock();
		}
	}
	public void signalAll_A(){
		lock.lock();
		try {
			System.out.println("signalAll_A begin ,time=" + System.currentTimeMillis() );
			conditionA.signal();
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
	}
	public void signalAll_B(){
		lock.lock();
		try {
			System.out.println("signalAll_B begin ,time=" + System.currentTimeMillis() );
			conditionB.signal();
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
	}
}
