package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
			// long变量的值, 一般不会超过最大值、也不会低于最小值, 用isInteger方法判断即可满足, 所以这里就暂时不做多余的判断, 上面setValue时同理, 也用isInteger方法判断
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
