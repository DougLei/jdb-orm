package com.douglei.database.dialect.impl.mysql;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.mysql.datatype.handler.MySqlDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.mysql.sql.SqlHandlerImpl;
import com.douglei.database.dialect.impl.mysql.table.MySqlTableHandlerImpl;

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
		super.tableHandler = new MySqlTableHandlerImpl();
		super.sqlHandler = new SqlHandlerImpl();
		super.dataTypeHandlerMapping = new MySqlDataTypeHandlerMapping();
	}

	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return true;
	}
}
