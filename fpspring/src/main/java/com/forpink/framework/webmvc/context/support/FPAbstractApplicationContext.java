package com.forpink.framework.webmvc.context.support;

/**
 * IOC顶层设计
 * @author administrator
 */
public abstract class FPAbstractApplicationContext {
	//受保护只提供给子类重写
	public void refresh() throws Exception {};
}
