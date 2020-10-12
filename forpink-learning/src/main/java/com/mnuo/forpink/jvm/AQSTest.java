package com.mnuo.forpink.jvm;

import java.util.concurrent.locks.ReentrantLock;

public class AQSTest{
	public static void main(String[] args) {
		ReentrantLock rr = new ReentrantLock(true);//公平锁
	}
}
