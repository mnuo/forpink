package com.mnuo.forpink.thread.char3.productconsumer;

public class ThreadP extends Thread{
	private P p;
	
	public ThreadP(P p){
		this.p = p;
	}
	@Override
	public void run() {
		while (true) {
			p.setValue();
		}
	}
}
