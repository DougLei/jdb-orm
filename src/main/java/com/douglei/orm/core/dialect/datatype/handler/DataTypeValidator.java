package com.douglei.orm.core.dialect.datatype.handler;

import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 数据类型验证器
 * 验证值的数据类型, 数据长度, 数据精度
 * @author DougLei
 */
public interface DataTypeValidator {
	
	/**
	 * 验证输入value的数据类型, 数据长度, 数据精度
	 * @param validateFieldName
	 * @param value
	 * @param length
	 * @param precision
	 * @return 
	 */
	ValidationResult doValidate(String validateFieldName, Object value, short length, short precision);
}
