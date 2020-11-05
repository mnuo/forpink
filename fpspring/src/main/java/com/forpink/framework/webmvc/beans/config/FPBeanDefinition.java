package com.forpink.framework.webmvc.beans.config;

import lombok.Data;

/**
 * 用来存储配置文件中的信息
 * 相当于保存在内存中的配置
 * @author administrator
 */
@Data
public class FPBeanDefinition {
	private String beanClassName;//原生bean的全类名
	private boolean lazyInit = false;//标记是否延迟加载
	private String factoryBeanName;//保存beanName, 在IOC容器中存储的key
}
