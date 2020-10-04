package com.douglei.orm.mapping.metadata.validator.internal;

import com.douglei.orm.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 数据类型验证器, 包括验证长度和精度
 * @author DougLei
 */
public class _DataTypeValidator extends Validator {
	private static final long serialVersionUID = -8818633371835369116L;
	private DataTypeHandler dataTypeHandler;
	private short length;
	private short precision;
	
	public _DataTypeValidator(DataTypeHandler dataTypeHandler, short length, short precision) {
		this.dataTypeHandler = dataTypeHandler;
		this.length = length;
		this.precision = precision;
	}

	@Override
	public ValidationResult validate(String fieldName, Object value) {
		return dataTypeHandler.doValidate(fieldName, value, length, precision);
	}
}
