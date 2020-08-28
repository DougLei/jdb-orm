package com.douglei.orm.configuration;

import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.core.dialect.Dialect;

/**
 * 
 * @author DougLei
 */
public class EnvironmentContext {
	private static ThreadLocal<EnvironmentProperty> ENVIRONMENT_PROPERTY = new ThreadLocal<EnvironmentProperty>();
	
	/**
	 * 设置配置的环境全局属性
	 * @param environmentProperty
	 */
	public static void setConfigurationEnvironmentProperty(EnvironmentProperty environmentProperty) {
		ENVIRONMENT_PROPERTY.set(environmentProperty);
	}
	
	/**
	 * 获取配置的环境全局属性
	 * @return
	 */
	public static EnvironmentProperty getEnvironmentProperty() {
		return ENVIRONMENT_PROPERTY.get();
	}
	
	/**
	 * 获取方言对象
	 * @return
	 */
	public static Dialect getDialect() {
		return ENVIRONMENT_PROPERTY.get().getDialect();
	}
}