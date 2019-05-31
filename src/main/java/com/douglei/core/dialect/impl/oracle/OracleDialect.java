package com.douglei.core.dialect.impl.oracle;

import com.douglei.core.dialect.DialectType;
import com.douglei.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class OracleDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.ORACLE;
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}
}
