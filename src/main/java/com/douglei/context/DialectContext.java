package com.douglei.context;

import com.douglei.database.dialect.Dialect;

/**
 * dialect 上下文
 * @author DougLei
 */
public class DialectContext {
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