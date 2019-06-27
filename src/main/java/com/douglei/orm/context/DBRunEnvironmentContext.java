package com.douglei.orm.context;

import com.douglei.orm.configuration.environment.property.EnvironmentProperty;

/**
 * 数据库运行环境 上下文
 * @author DougLei
 */
public class DBRunEnvironmentContext {
	private static final ThreadLocal<EnvironmentProperty> ENVIRONMENT_PROPERTY = new ThreadLocal<EnvironmentProperty>();
	
	/**
	 * 设置配置的环境全局属性
	 * @param environmentProperty
	 */
	public static void setConfigurationEnvironmentProperty(EnvironmentProperty environmentProperty) {
		ENVIRONMENT_PROPERTY.set(environmentProperty);
	}
	
	public static EnvironmentProperty getEnvironmentProperty() {
		return ENVIRONMENT_PROPERTY.get();
	}
}