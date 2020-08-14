package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Number;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype.DoubleDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype.LongDataTypeHandler;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public class NumberDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = -7147328253187136682L;
	private NumberDBDataTypeHandler() {}
	private static final NumberDBDataTypeHandler instance = new NumberDBDataTypeHandler();
	public static final NumberDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Number.singleInstance();
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null) {
			String valueString = value.toString();
			if(VerifyTypeMatchUtil.isInteger(valueString)) {
				LongDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
			}else if(VerifyTypeMatchUtil.isDouble(valueString)) {
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
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		String valueString = value.toString();
		if(VerifyTypeMatchUtil.isInteger(valueString)) {
			return LongDataTypeHandler.singleInstance().doValidate(validateFieldName, valueString, length, precision);
		}else if(VerifyTypeMatchUtil.isDouble(valueString)) {
			return DoubleDataTypeHandler.singleInstance().doValidate(validateFieldName, valueString, length, precision);
		}
		return new ValidationResult(validateFieldName) {
			
			@Override
			public String getOriginMessage() {
				return "数据值类型错误, 应为数字类型";
			}
			
			@Override
			public String getCode() {
				return codePrefix + "value.datatype.error.number";
			}
		};
	}
}
