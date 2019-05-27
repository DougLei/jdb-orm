package com.douglei.database.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.Datetime;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype.DateDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DatetimeDBDataTypeHandler extends DBDataTypeHandler{
	private DatetimeDBDataTypeHandler() {}
	private static final DatetimeDBDataTypeHandler instance = new DatetimeDBDataTypeHandler();
	public static final DatetimeDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Datetime.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Datetime.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		DateDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getTimestamp(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getTimestamp(columnIndex);
	}
}
