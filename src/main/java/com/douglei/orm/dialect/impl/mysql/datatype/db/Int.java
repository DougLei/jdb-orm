package com.douglei.orm.dialect.impl.mysql.datatype.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.impl.AbstractInt;

/**
 * 
 * @author DougLei
 */
public class Int extends AbstractInt{
	
	public Int() {
		super("INT", 4, 10);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {int.class, Integer.class};
	}
	
	@Override
	protected Object parseIntValue(String value) {
		return Integer.parseInt(value);
	}
	
	@Override
	protected void setIntValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		preparedStatement.setInt(parameterIndex, (int)value);
	}
	
	@Override
	public Integer getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		Object value = resultSet.getObject(columnIndex);
		if(value == null)
			return null;
		return Integer.parseInt(value.toString());
	}
	
	@Override
	public Integer getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		Object value = callableStatement.getObject(parameterIndex);
		if(value == null)
			return null;
		return Integer.parseInt(value.toString());
	}
}
