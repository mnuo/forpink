package com.forpink.framework.webmvc.context.support;
/**
 * 对配置文件进行查找,读取,解析
 * @author administrator
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.forpink.framework.webmvc.beans.config.FPBeanDefinition;

import lombok.Data;
@Data
public class FPBeanDefinitionReader {
	private List<String> registyBeanClasses = new ArrayList<>();
	private Properties config = new Properties();
	
	private final String SCAN_PACKAGE = "scanPackage";
	
	public FPBeanDefinitionReader(String... locations) {
		try (InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(locations[0].replace("classpath:", ""));) {
			config.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		doScanner(config.getProperty(SCAN_PACKAGE));
	}
	public void doScanner(String scanPackage){
		URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.", "/"));
		File classpath = new File(url.getFile());
		for (File file : classpath.listFiles()) {
			if(file.isDirectory()){
				doScanner(scanPackage + "." + file.getName());
			} else {
				if(!file.getName().endsWith(".class")){continue;}
				String className = scanPackage + "." + file.getName().replace(".class", "");
				registyBeanClasses.add(className);
			}
		}
	}
	public List<FPBeanDefinition> loadBeanDefinitions(){
		List<FPBeanDefinition> result = new ArrayList<>();
		try {
			for (String className : registyBeanClasses) {
				Class<?> beanClass = Class.forName(className);
				if(beanClass.isInterface()){
					continue;
				}
				result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()), beanClass.getName()));
				Class<?>[] interfaces = beanClass.getInterfaces();
				for (Class<?> i : interfaces) {
					result.add(doCreateBeanDefinition(i.getName(), beanClass.getName()));
					
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//把每一个配置信息解析成一个BeanDefinition
	private FPBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
		FPBeanDefinition beanDefinition = new FPBeanDefinition();
		beanDefinition.setBeanClassName(beanClassName);
		beanDefinition.setFactoryBeanName(factoryBeanName);
		return beanDefinition;
	}
	/**
	 * 将类名首字母改为小写
	 * 为了简化程序逻辑, 就不做其他判断了
	 * @param simpleName
	 * @return
	 */
	private String toLowerFirstCase(String simpleName) {
		char[] chars = simpleName.toCharArray();
		//大小写字母的ASCII码相差32,而且大写字母的ASCII码比小写字母小
		chars[0] += 32;
		return String.valueOf(chars);
	}
}
