package com.douglei.context;

import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.dialect.Dialect;
import com.douglei.database.metadata.table.CreateMode;

/**
 * 数据库信息 上下文
 * @author DougLei
 */
public class DBContext {
	private static final ThreadLocal<DB> DB_ = new ThreadLocal<DB>();
	private static DB getDB() {
		DB db = DB_.get();
		if(db == null) {
			db = new DB();
			DB_.set(db);
		}
		return db;
	}
	
	/**
	 * 设置配置的环境全局属性
	 * @param environmentProperty
	 */
	public static void setConfigurationEnvironmentProperty(EnvironmentProperty environmentProperty) {
		DB db = getDB();
		db.dialect = environmentProperty.getDialect();
		db.tableCreateMode = environmentProperty.getTableCreateMode();
	}
	
	/**
	 * 获取方言
	 * @return
	 */
	public static Dialect getDialect() {
		return getDB().dialect;
	}
	
	/**
	 * 获取(全局的)表创建模式
	 * @return
	 */
	public static CreateMode getTableCreateMode() {
		return getDB().tableCreateMode;
	}
}