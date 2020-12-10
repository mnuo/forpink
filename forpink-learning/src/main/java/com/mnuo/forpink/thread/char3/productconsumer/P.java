package com.mnuo.forpink.thread.char3.productconsumer;

public class P {
	private String lock;
	public P(String lock){
		super();
		this.lock = lock;
	}
	public void setValue(){
		try {
			synchronized (lock) {
				if(!ValueObject.value.equals("")){
					lock.wait();
				}
				String value = System.currentTimeMillis() + "_" + System.nanoTime();
				System.out.println("set值是: " + value);
				ValueObject.value = value;
				lock.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
