package com.douglei.sessions;

import com.douglei.database.dialect.Dialect;

/**
 * 当前系统运行时Dialect对象持有者
 * @author DougLei
 */
public class LocalRunDialectHolder {
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