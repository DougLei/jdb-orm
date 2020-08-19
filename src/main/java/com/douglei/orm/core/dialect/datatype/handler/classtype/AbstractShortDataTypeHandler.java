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
public abstract class AbstractShortDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = 6652217805128180607L;

	@Override
	public String getCode() {
		return DataType.SHORT.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {short.class, Short.class};
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && VerifyTypeMatchUtil.isLimitShort(value.toString())) {
			if(value.getClass() == short.class || value instanceof Short) {
				preparedStatement.setShort(parameterIndex, (short)value);
			}else {
				preparedStatement.setShort(parameterIndex, Short.parseShort(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value.getClass() == short.class || value instanceof Short || VerifyTypeMatchUtil.isInteger(value.toString())) {
			String string = value.toString();
			
			long l = Long.parseLong(string);
			if(l > Short.MAX_VALUE || l < Short.MIN_VALUE) 
				return new ValidationResult(validateFieldName, "数据值大小异常, 应在%d至%d范围内", "jdb.data.validator.value.digital.range.overflow", Short.MIN_VALUE, Short.MAX_VALUE);
			
			if(length != DBDataType.NO_LIMIT && string.length() > length) 
				return new ValidationResult(validateFieldName, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.digital.length.overlength", length, string.length());
			
			return null;
		}
		return new ValidationResult(validateFieldName, "数据值类型错误, 应为短整型(short)", "jdb.data.validator.value.datatype.error.short");
	}
}
