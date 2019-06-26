package com.douglei.orm.context;

import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.metadata.table.CreateMode;

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
	
	/**
	 * 获取方言
	 * @return
	 */
	public static Dialect getDialect() {
		return ENVIRONMENT_PROPERTY.get().getDialect();
	}
	
	/**
	 * 获取全局的表创建模式
	 * @return
	 */
	public static CreateMode getTableCreateMode() {
		return ENVIRONMENT_PROPERTY.get().getTableCreateMode();
	}
	
	/**
	 * 获取Configuration的id
	 * @return
	 */
	public static String getConfigurationId() {
		return ENVIRONMENT_PROPERTY.get().getId();
	}
	
	/**
	 * 获取序列化文件根路径
	 * @return
	 */
	public static String getSerializationFileRootPath() {
		return ENVIRONMENT_PROPERTY.get().getSerializationFileRootPath();
	}
	
	/**
	 * 获取是否开启数据验证
	 * @return
	 */
	public static boolean getEnableDataValidation() {
		return ENVIRONMENT_PROPERTY.get().getEnableDataValidation();
	}
}