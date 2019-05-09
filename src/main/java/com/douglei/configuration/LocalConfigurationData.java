package com.douglei.configuration;

import com.douglei.database.dialect.Dialect;

/**
 * 当前配置相关data对象
 * @author DougLei
 */
public class LocalConfigurationData {
	private static final ThreadLocal<Dialect> data = new ThreadLocal<Dialect>();
	
	public static void setDialect(Dialect dialect) {
		data.set(dialect);
	}
	public static Dialect getDialect() {
		return data.get();
	}
}

