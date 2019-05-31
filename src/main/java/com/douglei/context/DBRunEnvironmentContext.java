package com.douglei.context;

import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.core.dialect.Dialect;

/**
 * 数据库运行环境 上下文
 * @author DougLei
 */
public class DBRunEnvironmentContext {
	private static final ThreadLocal<DBRunEnvironment> DB_RUN_ENVIRONMENT = new ThreadLocal<DBRunEnvironment>();
	private static DBRunEnvironment getDBRunEnvironment() {
		DBRunEnvironment dbRunEnvironment = DB_RUN_ENVIRONMENT.get();
		if(dbRunEnvironment == null) {
			dbRunEnvironment = new DBRunEnvironment();
			DB_RUN_ENVIRONMENT.set(dbRunEnvironment);
		}
		return dbRunEnvironment;
	}
	
	/**
	 * 设置配置的环境全局属性
	 * @param environmentProperty
	 */
	public static void setConfigurationEnvironmentProperty(EnvironmentProperty environmentProperty) {
		DBRunEnvironment dbRunEnvironment = getDBRunEnvironment();
		dbRunEnvironment.dialect = environmentProperty.getDialect();
	}
	
	/**
	 * 获取方言
	 * @return
	 */
	public static Dialect getDialect() {
		return getDBRunEnvironment().dialect;
	}
}