package com.forpink.mvcframework.v1.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		try (InputStream is = null) {
			Properties configContext = new Properties();
//			this.getClass().getClassLoader().getre
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
