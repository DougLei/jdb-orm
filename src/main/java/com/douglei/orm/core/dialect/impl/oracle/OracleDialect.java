package com.douglei.orm.core.dialect.impl.oracle;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class OracleDialect extends AbstractDialect{
	
	@Override
	public DialectType getType() {
		return DialectType.ORACLE;
	}
}
