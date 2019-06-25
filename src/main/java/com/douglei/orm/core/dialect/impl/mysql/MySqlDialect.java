package com.douglei.orm.core.dialect.impl.mysql;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class MySqlDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.MYSQL;
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}
}
