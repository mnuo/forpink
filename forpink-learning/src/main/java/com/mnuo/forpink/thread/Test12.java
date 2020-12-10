package com.mnuo.forpink.thread;

public class Test12 {
	public static void main(String[] args) {
		
		try {
			PublicVar publicVar = new PublicVar();
			ThreadA1 thread = new ThreadA1(publicVar);
			thread.start();
			Thread.sleep(200);
			publicVar.getValue();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
class PublicVar {
	public String userName = "A";
	public String password="AA";
	synchronized public void setValue(String username, String password){
		try {
			this.userName = username;
			Thread.sleep(5000);
			this.password = password;
			System.out.println("setValue method thread name=" + Thread.currentThread().getName()
					+ " username=" + username + ", password=" + password);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getValue(){
		System.out.println("getValue method thread name=" + Thread.currentThread().getName()
				+ " username=" + userName + ", password=" + password);
	}
	
}
class ThreadA1 extends Thread{
	public PublicVar publicVar;
	public ThreadA1(PublicVar publicVar){
		super();
		this.publicVar = publicVar;
	}
	
	@Override
	public void run() {
		super.run();
		publicVar.setValue("B", "BB");
	}
}
