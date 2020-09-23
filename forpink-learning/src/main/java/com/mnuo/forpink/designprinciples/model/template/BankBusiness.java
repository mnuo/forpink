package com.mnuo.forpink.designprinciples.model.template;

public abstract class BankBusiness {
	
	public void takeNumber(){
		System.out.println("Queue for number");
	}
	public abstract void transact();
	public void evaluate(){
		System.out.println("feedback score");
	}
	public final void process(){
		takeNumber();
		transact();
		evaluate();
	}
	public static void main(String[] args) {
		BankBusiness bb = new BankBusiness() {
			@Override
			public void transact() {
				System.out.println("do something...");
			}
		};
		bb.process();
	}
}
