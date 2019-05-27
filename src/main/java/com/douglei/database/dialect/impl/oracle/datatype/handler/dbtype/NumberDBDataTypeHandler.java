package com.douglei.database.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.handler.classtype.DoubleDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.classtype.IntegerDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.classtype.LongDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

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
		return OracleDBType.NUMBER.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.NUMBER.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isDouble(value)) {
			DoubleDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
		}else if(ValidationUtil.isInteger(value)) {
			IntegerDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
		}else if(ValidationUtil.isLong(value)){
			LongDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		if(callableStatement.getMetaData().getScale(parameterIndex) == 0) {
			long value = callableStatement.getLong(parameterIndex);
			if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
				return (int)value;
			}
			return value;
		}
		return callableStatement.getDouble(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		if(rs.getMetaData().getScale(columnIndex) == 0) {
			long value = rs.getLong(columnIndex);
			if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
				return (int)value;
			}
			return value;
		}
		return rs.getDouble(columnIndex);
	}
}
