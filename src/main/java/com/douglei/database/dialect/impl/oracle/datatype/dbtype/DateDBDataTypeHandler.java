package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.classtype.OracleDateDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class DateDBDataTypeHandler extends DBDataTypeHandler{
	private DateDBDataTypeHandler() {}
	private static final DateDBDataTypeHandler instance = new DateDBDataTypeHandler();
	public static final DateDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return OracleDBType.DATE.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.DATE.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		OracleDateDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getTimestamp(parameterIndex);
	}
}
