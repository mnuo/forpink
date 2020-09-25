package com.mnuo.forpink.designprinciples.model.strategy;

public class Context {
	private Customer customer;
	public Context(Customer customer){
		this.customer = customer;
	}
	
	public void getReadPrice(double price){
		System.out.println("the price is: " + customer.getDiscoutPrice(price));
	}
	
	public static void main(String[] args) {
		Context context = new Context(new DefaultCustomer());
		context.getReadPrice(200);
		Context context1 = new Context(new VIPCustomer());
		context1.getReadPrice(200);
		Context context2 = new Context(new OldCustomer());
		context2.getReadPrice(200);
	}
}
