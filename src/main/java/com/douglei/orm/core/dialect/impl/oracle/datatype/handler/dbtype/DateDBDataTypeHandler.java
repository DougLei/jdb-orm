package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Date;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype.DateDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DateDBDataTypeHandler extends DBDataTypeHandler{
	private DateDBDataTypeHandler() {}
	private static final DateDBDataTypeHandler instance = new DateDBDataTypeHandler();
	public static final DateDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Date.singleInstance().getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return Date.singleInstance().getSqlType();
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
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		return DateDataTypeHandler.singleInstance().doValidate(value, length, precision);
	}
}
