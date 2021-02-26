package com.douglei.orm.configuration.environment;

/**
 * 环境上下文
 * @author DougLei
 */
public class EnvironmentContext {
	private static final ThreadLocal<Environment> CONTEXT = new ThreadLocal<Environment>();
	
	/**
	 * 设置环境
	 * @param environment
	 */
	public static void setEnvironment(Environment environment) {
		CONTEXT.set(environment);
	}
	
	/**
	 * 获取环境
	 * @return
	 */
	public static Environment getEnvironment() {
		return CONTEXT.get();
	}
}