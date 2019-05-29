package com.douglei.database.dialect.impl.sqlserver;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.sqlserver.sql.SqlHandlerImpl;

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
	
	@Override
	protected void initialize() {
		super.sqlHandler = new SqlHandlerImpl();
		super.dataTypeHandlerMapping = new SqlServerDataTypeHandlerMapping();
	}
	
	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return true;
	}
}
