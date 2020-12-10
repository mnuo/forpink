package com.mnuo.forpink.thread;


public class Test13 {
	public static void main(String[] args) {
		Service service = new Service();
		ThreadB1 a = new ThreadB1(service);
		a.setName("A");
		a.start();
		ThreadB2 b = new ThreadB2(service);
		b.setName("B");
		b.start();
	}
}
class Service{
	public static void print(String stringparam){
		try {
			synchronized (stringparam) {
				while (true) {
					System.out.println(Thread.currentThread().getName());
						Thread.sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class ThreadB1 extends Thread{
	private Service service;
	public ThreadB1(Service service){
		this.service = service;
	}
	@Override
	public void run() {
		service.print("AA");
	}
}
class ThreadB2 extends Thread{
	private Service service;
	public ThreadB2(Service service){
		this.service = service;
	}
	@Override
	public void run() {
		service.print("AA");
	}
}