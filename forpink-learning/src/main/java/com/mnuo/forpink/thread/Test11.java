package com.mnuo.forpink.thread;

public class Test11 {
	public static void main(String[] args) {
        try {
        	Myservice object = new Myservice();
        	ThreadA threadA = new ThreadA(object);
        	threadA.start();
			Thread.sleep(100);
			ThreadB threadB = new ThreadB(object);
			threadB.start();
			Thread.sleep(3000);
			threadA.stop();
			System.out.println("stop()执行后，在下方开始打印username和password。");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class Myservice{
	private String username = "a";
	private String password = "b";
	synchronized public String getUsername() {
		return username;
	}
	synchronized public String getPassword() {
		return password;
	}
	synchronized public void printString(String username, String password){
		try {
			this.username = username;
			Thread.sleep(10000);
			this.password = password;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class ThreadA extends Thread{
	private Myservice object;
	public ThreadA(Myservice object){
		super();
		this.object = object;
	}
	@Override
	public void run() {
		object.printString("b", "bb");
	}
}
class ThreadB extends Thread{
	private Myservice object;
	public ThreadB(Myservice object){
		super();
		this.object = object;
	}
	@Override
	public void run() {
		System.out.println(object.getUsername());
		System.out.println(object.getPassword());
	}
}