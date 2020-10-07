package com.douglei.orm.dialect.impl.oracle;

import com.douglei.orm.dialect.DialectType;

/**
 * 
 * @author DougLei
 */
public class OracleDialectType extends DialectType {
	public static final int ID = 20;

	public OracleDialectType() {
		super(OracleDialect.class, ID, "oracle", 11);
	}
}
