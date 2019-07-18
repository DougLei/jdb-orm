package com.douglei.orm.core.dialect.impl.sqlserver;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class SqlServerDialect extends AbstractDialect{
	
	@Override
	public DialectType getType() {
		return DialectType.SQLSERVER;
	}
}
