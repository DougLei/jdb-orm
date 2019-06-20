package com.douglei.orm.core.dialect.impl.oracle;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class OracleDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.ORACLE;
	private static final String[] SUPPORT_VERSIONS = {"11.2.0.1.0"};
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}
	
	@Override
	public String[] supportVersions() {
		return SUPPORT_VERSIONS;
	}
}
