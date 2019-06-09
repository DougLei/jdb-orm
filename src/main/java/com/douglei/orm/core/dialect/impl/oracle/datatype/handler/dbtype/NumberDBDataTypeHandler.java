package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Number;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype.DoubleDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype.LongDataTypeHandler;
import com.douglei.tools.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class NumberDBDataTypeHandler extends DBDataTypeHandler{
	private NumberDBDataTypeHandler() {}
	private static final NumberDBDataTypeHandler instance = new NumberDBDataTypeHandler();
	public static final NumberDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Number.singleInstance().getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return Number.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null) {
			String valueString = value.toString();
			if(ValidationUtil.isInteger(valueString)) {
				LongDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
			}else if(ValidationUtil.isDouble(valueString)) {
				DoubleDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
			}
		}
		preparedStatement.setNull(parameterIndex, getSqlType());
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		if(callableStatement.getMetaData().getScale(parameterIndex) == 0) {
			return getIntValue(callableStatement.getLong(parameterIndex));
		}
		return callableStatement.getDouble(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		if(rs.getMetaData().getScale(columnIndex) == 0) {
			return getIntValue(rs.getLong(columnIndex));
		}
		return rs.getDouble(columnIndex);
	}
	
	// 获取整型值
	private Object getIntValue(long value) {
		if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
			return (short)value;
		}
		if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
			return (int)value;
		}
		return value;
	}
}
