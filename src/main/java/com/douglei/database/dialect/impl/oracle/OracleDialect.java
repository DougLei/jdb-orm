package com.douglei.database.dialect.impl.oracle;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.oracle.sql.SqlHandlerImpl;
import com.douglei.database.dialect.impl.oracle.table.OracleTableHandlerImpl;

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
	
	@Override
	protected void initialize() {
		super.tableHandler = new OracleTableHandlerImpl();
		super.sqlHandler = new SqlHandlerImpl();
		super.dataTypeHandlerMapping = new OracleDataTypeHandlerMapping();
	}
	
	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return false;
	}
}
