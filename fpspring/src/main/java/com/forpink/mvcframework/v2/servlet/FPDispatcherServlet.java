package com.forpink.mvcframework.v2.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forpink.mvcframework.annotation.FPAutowired;
import com.forpink.mvcframework.annotation.FPController;
import com.forpink.mvcframework.annotation.FPRequestMapping;
import com.forpink.mvcframework.annotation.FPService;

public class FPDispatcherServlet extends HttpServlet{
	private static final long serialVersionUID = 5819474319410520949L;
	Properties configContext = new Properties();
	List<String> clazzes = new ArrayList<>();
	private Map<String, Object> ioc = new HashMap<>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			this.doDispatch(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String url = req.getRequestURI();
		String contextPath = req.getContextPath();
		url = url.replace(contextPath, "").replaceAll("/+", "/");
		if(!this.ioc.containsKey(url)){
			resp.getWriter().write("404 Not Found!!");
			return ;
		}
		Method method = (Method) this.ioc.get(url);
		Map<String, String[]> params = req.getParameterMap();
		method.invoke(this.ioc.get(method.getDeclaringClass().getName()), new Object[]{req, resp, params.get("name")[0]});
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		doInitCofig(config.getInitParameter("contextConfigLocation"));//加载配置文件
		
		doScanner(configContext.getProperty("scanPackage"));//扫描相关类
		
		doInstance();//初始化扫描的类, 并把他们放入IOC容器中
		
		doAutowired();//依赖注入
		
		initHandlerMapping();//初始化mapping
		
		
		System.out.println("FP FRAMEWORK is init");
	}

	private void initHandlerMapping() {
		// TODO Auto-generated method stub
		
	}

	private void doAutowired() {
		for (Object object : ioc.values()) {
			if(object == null){continue;}
			Class clazz = object.getClass();
			if(clazz.isAnnotationPresent(FPController.class)){
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if(!field.isAnnotationPresent(FPAutowired.class)){continue;}
					FPAutowired autowired = field.getAnnotation(FPAutowired.class);
					String beanName = autowired.value();
					if("".equals(beanName)){beanName = field.getType().getName();}
					field.setAccessible(true);
					try {
						field.set(ioc.get(clazz.getName()), ioc.get(beanName));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void doInstance() {
		try {
			for (String className : clazzes) {
				System.out.println("className:" + className);
				if (!className.contains(".")) {
					continue;
				}
				Class<?> clazz = Class.forName(className);
				if (clazz.isAnnotationPresent(FPController.class)) {
					ioc.put(className, clazz.newInstance());
					String baseUrl = "";
					if (clazz.isAnnotationPresent(FPRequestMapping.class)) {
						FPRequestMapping requestMapping = clazz.getAnnotation(FPRequestMapping.class);
						baseUrl = requestMapping.value();
					}
					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						if (!method.isAnnotationPresent(FPRequestMapping.class)) {
							continue;
						}
						FPRequestMapping requestMapping = method.getAnnotation(FPRequestMapping.class);
						String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
						ioc.put(url, method);
						System.out.println("Mapped" + url + "," + method);
					}
				} else if (clazz.isAnnotationPresent(FPService.class)) {
					FPService service = clazz.getAnnotation(FPService.class);
					String beanName = service.value();
					if ("".equals(beanName)) {
						beanName = clazz.getName();
					}
					Object instance = clazz.newInstance();
					ioc.put(beanName, instance);
					System.out.println("beanName" + beanName + "," + instance);
					for (Class<?> i : clazz.getInterfaces()) {
						ioc.put(i.getName(), instance);
					}
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void doInitCofig(String initParameter) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(initParameter);) {
			configContext.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("无法读取配置文件...");
		}
		
	}

	private void doScanner(String scanPackage) {
		URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
		File classDir = new File(url.getFile());
		for (File file : classDir.listFiles()) {
			if(file.isDirectory()){
				doScanner(scanPackage + "." + file.getName());
			} else {
				if(!file.getName().endsWith(".class")){continue;}
				String clazzName = (scanPackage + "." + file.getName().replace(".class", ""));
				clazzes.add(clazzName);
			}
		}
		
	}
}
