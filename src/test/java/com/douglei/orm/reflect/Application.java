package com.douglei.orm.reflect;

public class Application {
	public static void main(String[] args) throws Exception {
		Class<?> clz = Class.forName("com.douglei.test.reflect.Entity");
		
		System.out.println(clz.getMethod("singleInstance").invoke(null));
		System.out.println(clz.getMethod("singleInstance").invoke(null));
		System.out.println(clz.getMethod("singleInstance").invoke(null));
		System.out.println(clz.getMethod("singleInstance").invoke(null));
	}
}
