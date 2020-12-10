package com.mnuo.forpink.thread;

public class Test14 {
	public static void main(String[] args) {
		DealThread a = new DealThread();
		a.setFlag("a");
		Thread t1 = new Thread(a);
		t1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread t2 = new Thread(a);
		a.setFlag("b");
		t2.start();
		
	}

}
class DealThread implements Runnable{
	public String username;
	public Object lock1 = new Object();
	public Object lock2 = new Object();
	public void setFlag(String username) {
		this.username = username;
	}
	@Override
	public void run() {
		if(username.equals("a")){
			synchronized (lock1) {
				try {
					System.out.println("username: " + username);
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				synchronized (lock2) {
					System.out.println("按lock1-> lock2顺序执行.");
				}
			}
		}else{
			synchronized (lock2) {
				try {
					System.out.println("username: " + username);
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				synchronized (lock1) {
					System.out.println("按lock2-> lock1顺序执行.");
				}
			}
		}
	}
	
}