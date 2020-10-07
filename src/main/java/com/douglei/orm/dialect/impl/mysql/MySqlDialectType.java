package com.douglei.orm.dialect.impl.mysql;

import com.douglei.orm.dialect.DialectType;

/**
 * 
 * @author DougLei
 */
public class MySqlDialectType extends DialectType {
	public static final int ID = 10;

	public MySqlDialectType() {
		super(MySqlDialect.class, ID, "mysql", 8);
	}
}
