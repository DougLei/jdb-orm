package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.classtype.MySqlIntegerDataTypeHandler;

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
		return MySqlDBType.INT.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.INT.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		MySqlIntegerDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getInt(parameterIndex);
	}
}
