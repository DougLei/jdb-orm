package com.douglei.orm.dialect.impl.sqlserver;

import com.douglei.orm.dialect.DialectType;

/**
 * 
 * @author DougLei
 */
public class SqlServerDialectType extends DialectType {
	public static final int ID = 30;

	public SqlServerDialectType() {
		super(SqlServerDialect.class, ID, "sqlserver", 11);
	}
}
