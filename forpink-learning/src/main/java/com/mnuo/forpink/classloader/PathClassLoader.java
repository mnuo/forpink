package com.mnuo.forpink.classloader;

public class PathClassLoader extends ClassLoader {
	private String classPath;
	
	public PathClassLoader(String classPath){
		this.classPath = classPath;
	}
	
	protected Class<?> findClass(String name) {
//		if(packageName.startWith(name)){
			byte[] classDate = getData(name);
			if(classDate == null){
				try {
					throw new Exception("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//		}
			return null;
	}

	private byte[] getData(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
