package com.douglei.orm.dialect.impl.oracle.datatype.db;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.impl.AbstractDecimal;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public class Number extends AbstractDecimal{
	private static final long serialVersionUID = 3801515554935666998L;
	private static final Number singleton = new Number();
	public static Number getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Number() {
		super(2);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {byte.class, short.class, int.class, long.class, double.class, float.class, Byte.class, Short.class, Integer.class, Long.class, Double.class, Float.class, BigDecimal.class};
	}
	
	@Override
	protected void setValue_(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		Class<?> valueClass = value.getClass();
		if(valueClass == int.class || value instanceof Integer) {
			preparedStatement.setInt(parameterIndex, (int)value);
		}else if(valueClass == long.class || value instanceof Long) {
			preparedStatement.setLong(parameterIndex, (long)value);
		}else if(valueClass == short.class || value instanceof Short) {
			preparedStatement.setShort(parameterIndex, (short)value);
		}else if(valueClass == byte.class || value instanceof Byte) {
			preparedStatement.setByte(parameterIndex, (byte)value);
		}else {
			super.setValue_(preparedStatement, parameterIndex, valueClass);
		}
	}
	
	@Override
	protected boolean validateType(Object value) {
		Class<?> valueClass = value.getClass();
		return valueClass == int.class || value instanceof Integer || valueClass == long.class || value instanceof Long || valueClass == short.class || value instanceof Short || valueClass == byte.class || value instanceof Byte || valueClass == double.class || value instanceof Double || valueClass == float.class || value instanceof Float || value instanceof BigDecimal || VerifyTypeMatchUtil.isDouble(value.toString());
	}
}
