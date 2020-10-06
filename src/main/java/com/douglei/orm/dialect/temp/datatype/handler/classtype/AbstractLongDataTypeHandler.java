package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.DataType2;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractLongDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -6024285035307877367L;

	@Override
	public String getCode() {
		return DataType2.LONG.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {long.class, Long.class};
	}

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
			if(length != DBDataType.NO_LIMIT && string.length() > length) 
				return new ValidationResult(validateFieldName, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.digital.length.overlength", length, string.length());
			return null;
		}
		return new ValidationResult(validateFieldName, "数据值类型错误, 应为长整型(long)", "jdb.data.validator.value.datatype.error");
	}
}
