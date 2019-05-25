package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.impl.DateDataTypeHandler;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class DateDBDataTypeHandler extends DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "date";
	}
	
	@Override
	public int getSqlType() {
		return 93;
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		DateDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getTimestamp(parameterIndex);
	}
}
