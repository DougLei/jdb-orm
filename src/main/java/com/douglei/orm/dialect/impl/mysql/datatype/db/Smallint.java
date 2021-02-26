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
public class Smallint extends AbstractInt{
	private static final Smallint singleton = new Smallint();
	public static Smallint getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	protected Smallint() {
		super("SMALLINT", 5, 5);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {short.class, byte.class, Short.class, Byte.class};
	}
	
	@Override
	protected Object parseIntValue(String value) {
		return Short.parseShort(value);
	}
	
	@Override
	protected void setIntValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		preparedStatement.setShort(parameterIndex, (short)value);
	}
	
	@Override
	public Short getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		Object value = resultSet.getObject(columnIndex);
		if(value == null)
			return null;
		return Short.parseShort(value.toString());
	}
	
	@Override
	public Short getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		Object value = callableStatement.getObject(parameterIndex);
		if(value == null)
			return null;
		return Short.parseShort(value.toString());
	}
}
