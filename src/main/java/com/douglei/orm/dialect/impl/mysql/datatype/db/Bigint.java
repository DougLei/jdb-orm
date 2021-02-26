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
public class Bigint extends AbstractInt{
	private static final Bigint singleton = new Bigint();
	public static Bigint getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	protected Bigint() {
		super("BIGINT", -5, 19);
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
		Object value = resultSet.getObject(columnIndex);
		if(value == null)
			return null;
		return Long.parseLong(value.toString());
	}
	
	@Override
	public Long getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		Object value = callableStatement.getObject(parameterIndex);
		if(value == null)
			return null;
		return Long.parseLong(value.toString());
	}
}
