package com.mnuo.forpink.designprinciples.model.observer;

public class Test {
	public static void main(String[] args) {
		Observer1 observer = new Observer1();
		ConcretSubject sub = new ConcretSubject();
		Subject.list.add(observer);
		sub.exChange();
	}
}
