package com.mnuo.forpink.thread.char3.productconsumer;

public class ThreadC extends Thread{
	private C c;
	public ThreadC(C c){
		this.c = c;
	}
	@Override
	public void run() {
		while (true) {
			c.getValue();
		}
	}
}
