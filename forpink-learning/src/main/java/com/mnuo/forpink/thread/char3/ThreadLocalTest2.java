package com.mnuo.forpink.thread.char3;

public class ThreadLocalTest2 {
	private static ThreadLocalExt t1 = new ThreadLocalExt();
	public static void main(String[] args) {
		if(t1.get() == null){
			System.out.println("never set value.");
			t1.set("This value");
		}
		System.out.println(t1.get());
		System.out.println(t1.get());
	}
}

class ThreadLocalExt extends ThreadLocal<String>{
	@Override
	protected String initialValue() {
		return "default Value, The First get() is not null.";
	}
}