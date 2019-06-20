package com.douglei.orm.core.dialect.impl.sqlserver;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class SqlServerDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.SQLSERVER;
	private static final String[] SUPPORT_VERSIONS = {"2012 - 11.0.2100.60"};
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}

	@Override
	public String[] supportVersions() {
		return SUPPORT_VERSIONS;
	}
}
