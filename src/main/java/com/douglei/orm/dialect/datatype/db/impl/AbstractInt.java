package com.douglei.orm.dialect.datatype.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractInt extends DBDataType {
	private static final long serialVersionUID = -1328013077879682555L;

	protected AbstractInt(int sqlType, int maxLength) {
		super(sqlType, maxLength);
	}

	@Override
	protected final void setValue_(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		Class<?> valueClass = value.getClass();
		if(valueClass == int.class || value instanceof Integer || valueClass == long.class || value instanceof Long || valueClass == short.class || value instanceof Short || valueClass == byte.class || value instanceof Byte){
			setIntValue(preparedStatement, parameterIndex, (long)value);
		}else if(VerifyTypeMatchUtil.isInteger(value.toString())){
			setIntValue(preparedStatement, parameterIndex, parseIntValue(value.toString()));
		}else {
			super.setValue_(preparedStatement, parameterIndex, value);
		}
	}
	
	/**
	 * 解析出整数类型的值
	 * @param value
	 * @return
	 */
	protected abstract Object parseIntValue(String value);
	
	/**
	 * 设置整数类型的值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	protected abstract void setIntValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException;
	
	@Override
	public final ValidationResult validate(String name, Object value, int length, int precision) {
		Class<?> valueClass = value.getClass();
		if(valueClass == int.class || value instanceof Integer || valueClass == long.class || value instanceof Long || valueClass == short.class || value instanceof Short || valueClass == byte.class || value instanceof Byte || VerifyTypeMatchUtil.isInteger(value.toString())) {
			String string = value.toString();
			if(string.length() > length) 
				return new ValidationResult(name, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.digital.length.overlength", length, string.length());
			return null;
		}
		return new ValidationResult(name, "数据值类型错误, 应为整数类型", "jdb.data.validator.value.datatype.error.integer");
	}
}
