package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractLongDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -7544715947166673733L;

	@Override
	public String getCode() {
		return DataType.LONG.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {long.class, Long.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && VerifyTypeMatchUtil.isInteger(value.toString())) {
			if(value.getClass() == long.class || value instanceof Long) {
				preparedStatement.setLong(parameterIndex, (long)value);
			}else {
				preparedStatement.setLong(parameterIndex, Long.parseLong(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value.getClass() == long.class || value instanceof Long || VerifyTypeMatchUtil.isInteger(value.toString())) {
			String string = value.toString();
			if(length != DBDataType.NO_LIMIT && string.length() > length) {
				return new ValidationResult(validateFieldName) {
					
					@Override
					public String getMessage() {
						return "数据值长度超长, 设置长度为" + length +", 实际长度为" + string.length();
					}
					
					@Override
					public String getI18nCode() {
						return i18nCodePrefix + "value.digital.length.overlength";
					}

					@Override
					public Object[] getI18nParams() {
						return new Object[] { length, string.length() };
					}
				};
			}
			return null;
		}
		return new ValidationResult(validateFieldName) {
			
			@Override
			public String getMessage() {
				return "数据值类型错误, 应为长整型(long)";
			}
			
			@Override
			public String getI18nCode() {
				return i18nCodePrefix + "value.datatype.error.long";
			}
		};
	}
}
