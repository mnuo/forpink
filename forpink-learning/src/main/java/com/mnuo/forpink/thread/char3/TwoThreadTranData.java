package com.mnuo.forpink.thread.char3;

import java.util.ArrayList;
import java.util.List;

public class TwoThreadTranData {
	public static void main(String[] args) {
		MyList list = new MyList();
		ThreadA a = new ThreadA(list);
		a.setName("A");
		a.start();
		ThreadB b = new ThreadB(list);
		b.setName("B");
		b.start();
	}
}
class MyList{
	volatile private List<String> list = new ArrayList<String>();//以实现A线程和B线程间的可视性。
	public void add(){
		list.add("高宏烟");
	}
	public int size(){
		return list.size();
	}
}

class ThreadA extends Thread{
	private MyList myList;
	public ThreadA(MyList myList){
		this.myList = myList;
	}
	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				myList.add();
				System.out.println("添加了"+(i+1)+"个元素");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class ThreadB extends Thread{
	private MyList myList;
	public ThreadB(MyList myList){
		this.myList = myList;
	}
	@Override
	public void run() {
		try {
			while (true) {
				if(myList.size() == 5){
					System.out.println("==5了, 线程b要退出了");
					throw new InterruptedException();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}