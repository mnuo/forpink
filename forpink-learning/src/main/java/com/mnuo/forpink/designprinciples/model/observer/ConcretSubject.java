package com.mnuo.forpink.designprinciples.model.observer;

public class ConcretSubject implements Subject{

	@Override
	public void publicMessae() {
		System.out.println("public massage");
	}

	@Override
	public void cancalMessag() {
		System.out.println("public cancal");
	}

	@Override
	public void exChange() {
		for (Observer obs : list) {
			// 更新每一个观察者中的信息
			obs.update();
		}
	}
	
}
