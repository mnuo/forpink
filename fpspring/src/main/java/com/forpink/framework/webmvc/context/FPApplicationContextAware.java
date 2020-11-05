package com.forpink.framework.webmvc.context;

/**
 * 通过解耦模式获取IOC容器顶层设计
 * 后面将通过一个监听器去扫描所有的类, 只要去实现了次接口
 * 将自动调用setApplicationContext方法, 从而将IOC容器注入目标中
 * @author administrator
 */
public interface FPApplicationContextAware {
	void setApplicationContext(FPApplicationContext applicationContext);

}
