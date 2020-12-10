package com.mnuo.forpink.thread.char3.productconsumer;

public class C {
	private String lock;
	
	public C(String lock){
		super();
		this.lock = lock;
	}
	
	public void getValue(){
		try {
			synchronized (lock) {
				if(ValueObject.value.equals("")){
					lock.wait();
				}
				System.out.println("get值是: " + ValueObject.value);
				ValueObject.value = "";
				lock.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
