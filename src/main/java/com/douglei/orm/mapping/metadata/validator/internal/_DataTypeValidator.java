package com.douglei.orm.mapping.metadata.validator.internal;

import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 数据类型验证器, 包括验证长度和精度
 * @author DougLei
 */
public class _DataTypeValidator extends Validator {
	private DataType dataType;
	private int length;
	private int precision;
	
	public _DataTypeValidator(DataType dataType, int length, int precision) {
		this.dataType = dataType;
		this.length = length;
		this.precision = precision;
	}

	@Override
	public ValidationResult validate(String fieldName, Object value) {
		return dataType.validate(fieldName, value, length, precision);
	}
}
