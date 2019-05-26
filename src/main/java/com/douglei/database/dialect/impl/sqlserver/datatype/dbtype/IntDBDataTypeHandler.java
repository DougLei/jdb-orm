package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;
import com.douglei.database.dialect.impl.sqlserver.datatype.classtype.SqlServerIntegerDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class IntDBDataTypeHandler extends DBDataTypeHandler{
	private IntDBDataTypeHandler() {}
	private static final IntDBDataTypeHandler instance = new IntDBDataTypeHandler();
	public static final IntDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return SqlServerDBType.INT.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.INT.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		SqlServerIntegerDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getInt(parameterIndex);
	}
}
