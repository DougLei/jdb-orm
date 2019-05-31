package com.douglei.core.dialect.impl.sqlserver;

import com.douglei.core.dialect.DialectType;
import com.douglei.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class SqlServerDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.SQLSERVER;
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}
}
