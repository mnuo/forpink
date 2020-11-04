package com.forpink.mvcframework.v1.servlet;

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
	
	private Map<String, Object> mapping = new HashMap<>();
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
		if(!this.mapping.containsKey(url)){
			resp.getWriter().write("404 Not Found!!");
			return ;
		}
		Method method = (Method) this.mapping.get(url);
		Map<String, String[]> params = req.getParameterMap();
		method.invoke(this.mapping.get(method.getDeclaringClass().getName()), new Object[]{req, resp, params.get("name")[0]});
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		InputStream is = null;
		try {
			Properties configContext = new Properties();
			is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
			configContext.load(is);
			String scanPackage = configContext.getProperty("scanPackage");
			List<String> list = doScanner(scanPackage);
			for (String className : list) {
				System.out.println("className:" + className);
				if(!className.contains(".")){continue;}
				Class<?> clazz = Class.forName(className);
				if(clazz.isAnnotationPresent(FPController.class)){
					mapping.put(className, clazz.newInstance());
					String baseUrl = "";
					if(clazz.isAnnotationPresent(FPRequestMapping.class)){
						FPRequestMapping requestMapping = clazz.getAnnotation(FPRequestMapping.class);
						baseUrl = requestMapping.value();
					}
					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						if(!method.isAnnotationPresent(FPRequestMapping.class)){
							continue;
						}
						FPRequestMapping requestMapping = method.getAnnotation(FPRequestMapping.class);
						String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
						mapping.put(url, method);
						System.out.println("Mapped" + url + "," + method);
					}
				} else if(clazz.isAnnotationPresent(FPService.class)){
					FPService service = clazz.getAnnotation(FPService.class);
					String beanName = service.value();
					if("".equals(beanName)){
						beanName = clazz.getName();
					}
					Object instance = clazz.newInstance();
					mapping.put(beanName, instance);
					System.out.println("beanName" + beanName + "," + instance);
					for (Class<?> i : clazz.getInterfaces()) {
						mapping.put(i.getName(), instance);
					}
				} else {
					continue;
				}
			}
			for (Object object : mapping.values()) {
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
							field.set(mapping.get(clazz.getName()), mapping.get(beanName));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
		}finally {
			if(is != null){
				try{is.close();}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		System.out.println("FP FRAMEWORK is init");
	}

	private List<String> doScanner(String scanPackage) {
		List<String> list = new ArrayList<String>();
		URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
		File classDir = new File(url.getFile());
		for (File file : classDir.listFiles()) {
			if(file.isDirectory()){
				list.addAll(doScanner(scanPackage + "." + file.getName()));
			} else {
				if(!file.getName().endsWith(".class")){continue;}
				String clazzName = (scanPackage + "." + file.getName().replace(".class", ""));
				list.add(clazzName);
			}
		}
		return list;
		
	}
}
