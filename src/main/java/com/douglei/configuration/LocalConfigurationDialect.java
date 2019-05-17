package com.douglei.configuration;

import com.douglei.database.dialect.Dialect;

/**
 * 当前系统进行配置解析时Dialect对象
 * @author DougLei
 */
public class LocalConfigurationDialect {
	private static final ThreadLocal<Dialect> DIALECT = new ThreadLocal<Dialect>();
	
	public static void setDialect(Dialect dialect) {
		if(DIALECT.get() == null) {
			DIALECT.set(dialect);
		}
	}
	public static Dialect getDialect() {
		return DIALECT.get();
	}
}

