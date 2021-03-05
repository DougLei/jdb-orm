package com.douglei.orm.dialect.datatype.db.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.tools.datatype.DataTypeValidateUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDecimal extends DBDataType {

	protected AbstractDecimal(String name, int sqlType) {
		super(name, sqlType, 38, 38);
	}

	@Override
	public final String getSqlStatement(int length, int precision) {
		return name + "("+length+","+precision+")";
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {double.class, float.class, Double.class, Float.class, BigDecimal.class};
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		Class<?> valueClass = value.getClass();
		if(valueClass == double.class || value instanceof Double) {
			preparedStatement.setDouble(parameterIndex, (double)value);
		}else if(valueClass == float.class || value instanceof Float) {
			preparedStatement.setFloat(parameterIndex, (float)value);
		}else if(value instanceof BigDecimal) {
			preparedStatement.setBigDecimal(parameterIndex, (BigDecimal)value);
		}else if(DataTypeValidateUtil.isNumber(value.toString())){
			preparedStatement.setBigDecimal(parameterIndex, new BigDecimal(value.toString()));
		}else {
			super.setValue(preparedStatement, parameterIndex, value);
		}
	}
	
	@Override
	public final BigDecimal getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return resultSet.getBigDecimal(columnIndex);
	}
	
	@Override
	public final BigDecimal getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getBigDecimal(parameterIndex);
	}
	
	@Override
	public final ValidateFailResult validate(String name, Object value, int length, int precision) {
		if(validateType(value)) {
			String string = value.toString();
			if(string.length()-1 > length)
				return new ValidateFailResult(name, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.digital.length.overlength", length, (string.length() - 1));
			
			int dotIndex = string.indexOf("."); 
			if(dotIndex != -1) {
				int pl = string.substring(dotIndex+1).length();
				if(pl > precision) 
					return new ValidateFailResult(name, "数据值精度超长, 设置精度为%d, 实际精度为%d", "jdb.data.validator.value.digital.precision.overlength", precision, pl);
			}
			return null;
		}
		return new ValidateFailResult(name, "数据值类型错误, 应为浮点类型", "jdb.data.validator.value.datatype.error.double"); 
	}
	
	/**
	 * 验证value的class
	 * @param value
	 * @return
	 */
	protected boolean validateType(Object value) {
		Class<?> valueClass = value.getClass();
		return valueClass == double.class 
				|| value instanceof Double 
				|| valueClass == float.class 
				|| value instanceof Float 
				|| value instanceof BigDecimal 
				|| DataTypeValidateUtil.isNumber(value.toString());
	}
}
