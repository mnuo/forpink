package com.mnuo.forpink.thread.char3;

public class InheritableThreadLocalTest {

}

class InheritableTooles {
	public static InheritableThreadLocal<String> t1 = new InheritableThreadLocal<>();

}
class InheritableThreadA extends Thread{
	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}