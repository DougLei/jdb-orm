package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

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
	private static final Int singleton = new Int();
	public static Int getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Int() {
		super(4, 10);
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
		return resultSet.getInt(columnIndex);
	}
	
	@Override
	public Integer getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getInt(parameterIndex);
	}
}
