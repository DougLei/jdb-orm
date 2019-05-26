package com.douglei.database.dialect.impl.oracle;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.oracle.sql.SqlHandlerImpl;

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
		super.sqlHandler = new SqlHandlerImpl();
		super.dataTypeHandlerMapping = new OracleDataTypeHandlerMapping();
	}
	
	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return false;
	}
}
