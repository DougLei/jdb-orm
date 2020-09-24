package com.douglei.orm.configuration;

import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.core.dialect.Dialect;

/**
 * 
 * @author DougLei
 */
public class EnvironmentContext {
	private static ThreadLocal<EnvironmentProperty> PROPERTY = new ThreadLocal<EnvironmentProperty>();
	
	/**
	 * 设置配置的环境全局属性
	 * @param property
	 */
	public static void setProperty(EnvironmentProperty property) {
		PROPERTY.set(property);
	}
	
	/**
	 * 获取配置的环境全局属性
	 * @return
	 */
	public static EnvironmentProperty getProperty() {
		return PROPERTY.get();
	}
	
	/**
	 * 获取方言对象
	 * @return
	 */
	public static Dialect getDialect() {
		return PROPERTY.get().getDialect();
	}
}