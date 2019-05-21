package com.douglei.database.dialect.impl.mysql;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.mysql.sql.SqlHandlerImpl;

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
	
	@Override
	protected void initialize() {
		super.sqlHandler = new SqlHandlerImpl();
		super.dataTypeHandlerMapping = new MySqlDataTypeHandlerMapping();
	}
}
