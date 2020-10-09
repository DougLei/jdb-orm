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
public class Bigint extends AbstractInt{
	private static final Bigint singleton = new Bigint();
	public static Bigint getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Bigint() {
		super(-5, 19);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {long.class, Long.class};
	}
	
	@Override
	protected Object parseIntValue(String value) {
		return Long.parseLong(value);
	}
	
	@Override
	protected void setIntValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		preparedStatement.setLong(parameterIndex, (long)value);
	}
	
	@Override
	public Long getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return resultSet.getLong(columnIndex);
	}
	
	@Override
	public Long getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getLong(parameterIndex);
	}
}
