package com.douglei.orm.core.dialect.impl.mysql;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;

/**
 * 
 * @author DougLei
 */
public final class MySqlDialect extends AbstractDialect{
	
	@Override
	public DialectType getType() {
		return DialectType.MYSQL;
	}
}
