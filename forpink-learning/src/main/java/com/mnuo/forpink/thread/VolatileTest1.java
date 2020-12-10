package com.mnuo.forpink.thread;

public class VolatileTest1 {

}
class PrintString{
	private boolean isContinuePrint = true;
	private boolean isContinuePrint(){
		return isContinuePrint;
	}
	public void setContinuePrint(boolean isContinuePrint){
		this.isContinuePrint = isContinuePrint;
	}
	
	public void printStringMethod(){
		
	}
	
}