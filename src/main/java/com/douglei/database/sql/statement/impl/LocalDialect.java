package com.douglei.database.sql.statement.impl;

import com.douglei.database.dialect.Dialect;

/**
 * 当前Dialect对象
 * @author DougLei
 */
public class LocalDialect {
	private static final ThreadLocal<Dialect> data = new ThreadLocal<Dialect>();
	
	public static void setDialect(Dialect dialect) {
		if(data.get() == null) {
			data.set(dialect);
		}
	}
	public static Dialect getDialect() {
		return data.get();
	}
}

