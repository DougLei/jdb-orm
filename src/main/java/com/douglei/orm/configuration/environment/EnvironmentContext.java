package com.douglei.orm.configuration.environment;

/**
 * 
 * @author DougLei
 */
public class EnvironmentContext {
	private static final ThreadLocal<Environment> CONTEXT = new ThreadLocal<Environment>();
	
	public static void setEnvironment(Environment environment) {
		CONTEXT.set(environment);
	}
	public static Environment getEnvironment() {
		return CONTEXT.get();
	}
}