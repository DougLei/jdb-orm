package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.classtype.MySqlDateDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class DatetimeDBDataTypeHandler extends DBDataTypeHandler{
	private DatetimeDBDataTypeHandler() {}
	private static final DatetimeDBDataTypeHandler instance = new DatetimeDBDataTypeHandler();
	public static final DatetimeDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return MySqlDBType.DATETIME.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.DATETIME.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		MySqlDateDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getTimestamp(parameterIndex);
	}
}
