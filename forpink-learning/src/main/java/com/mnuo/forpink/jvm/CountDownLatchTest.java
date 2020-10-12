package com.mnuo.forpink.jvm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {
	private static final int ThreadNum = 10;
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDown = new CountDownLatch(ThreadNum);
		
		ExecutorService executor = Executors.newFixedThreadPool(ThreadNum);
		
		for(int i = 0; i < ThreadNum; i ++){
			executor.execute(new Person(countDown, i+1));
		}
		countDown.await();
	}
	
	
	static class Person implements Runnable{
		private CountDownLatch countDown;
		private int index;
		
		public Person(CountDownLatch countDown, int index){
			this.countDown = countDown;
			this.index = index;
		}
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("person " + index + " get in.");
			countDown.countDown();
		}
	}
}
