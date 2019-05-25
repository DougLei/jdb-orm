package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.impl.StringDataTypeHandler;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class VarcharDBDataTypeHandler extends DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "varchar";
	}

	@Override
	public int getSqlType() {
		return 12;
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		StringDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getString(parameterIndex);
	}
}
