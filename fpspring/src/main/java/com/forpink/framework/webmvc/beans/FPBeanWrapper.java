package com.forpink.framework.webmvc.beans;

import lombok.Data;

@Data
public class FPBeanWrapper {
	private Object wrappedInstance;
	private Class<?> wrappedClass;
	
	public FPBeanWrapper(Object wrappedInstance){
		this.wrappedInstance = wrappedInstance;
	}
	
	//返回代理以后的Class
	//可能是$Proxy0
	public Class<?> getWrappedClass(){
		return this.wrappedClass.getClass();
		
	}
}
