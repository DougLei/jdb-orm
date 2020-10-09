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
	private static final long serialVersionUID = -5426624634254372838L;
	private static final Smallint singleton = new Smallint();
	public static Smallint getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	protected Smallint() {
		super(5, 5);
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
		return resultSet.getShort(columnIndex);
	}
	
	@Override
	public Short getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getShort(parameterIndex);
	}
}
