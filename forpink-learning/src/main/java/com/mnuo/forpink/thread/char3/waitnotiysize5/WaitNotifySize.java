package com.mnuo.forpink.thread.char3.waitnotiysize5;

import java.util.ArrayList;
import java.util.List;

public class WaitNotifySize {
	public static void main(String[] args) {
		try {
			Object obj = new Object();
			ThreadA a = new ThreadA(obj);
			a.start();
			Thread.sleep(50);
			ThreadB b = new ThreadB(obj);
			b.start();
		} catch (Exception e) {
		}
	}
}

class MyList{
	private static List<String> list = new ArrayList<>();
	public static void add(){
		list.add("anything");
	}
	public static int size(){
		return list.size();
	}
}

class ThreadB extends Thread{
	private Object lock;
	
	public ThreadB(Object lock){
		super();
		this.lock = lock;
	}
	@Override
	public void run() {
		try {
			synchronized (lock) {
				for (int i = 0; i < 10; i++) {
					MyList.add();
					if(MyList.size() == 5){
						lock.notify();
						System.out.println("已发出通知");
					}
					System.out.println("添加了" + (i+1) + "个元素");
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

class ThreadA extends Thread{
	private Object lock;
	
	public ThreadA(Object lock){
		super();
		this.lock = lock;
	}
	@Override
	public void run() {
		try {
			synchronized (lock) {
				if(MyList.size() != 5){
					System.out.println("wait begin " + System.currentTimeMillis());
					lock.wait();
					System.out.println("wait end " + System.currentTimeMillis());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
