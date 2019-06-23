package com.douglei.orm.context;

import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.metadata.table.CreateMode;

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
		dbRunEnvironment.configurationId = environmentProperty.getId();
		dbRunEnvironment.dialect = environmentProperty.getDialect();
		dbRunEnvironment.tableCreateMode = environmentProperty.getTableCreateMode();
	}
	
	/**
	 * 设置Configuration的Id
	 * @param configurationId
	 */
	public static void setConfigurationId(String configurationId) {
		DBRunEnvironment dbRunEnvironment = getDBRunEnvironment();
		dbRunEnvironment.configurationId = configurationId;
	}
	
	/**
	 * 获取方言
	 * @return
	 */
	public static Dialect getDialect() {
		return getDBRunEnvironment().dialect;
	}
	
	/**
	 * 获取全局的表创建模式
	 * @return
	 */
	public static CreateMode getTableCreateMode() {
		return getDBRunEnvironment().tableCreateMode;
	}
	
	/**
	 * 获取Configuration的id
	 * @return
	 */
	public static String getConfigurationId() {
		return getDBRunEnvironment().configurationId;
	}
}