package com.douglei.orm.core.metadata.validator.internal;

import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 数据类型验证器, 包括验证长度和精度
 * @author DougLei
 */
public class _DataTypeValidator extends Validator {
	private static final long serialVersionUID = -6687368590871595167L;
	private DataTypeHandler dataTypeHandler;
	private short length;
	private short precision;
	
	public _DataTypeValidator(DataTypeHandler dataTypeHandler, short length, short precision) {
		this.dataTypeHandler = dataTypeHandler;
		this.length = length;
		this.precision = precision;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public ValidatorResult doValidate(Object value) {
		return dataTypeHandler.doValidate(value, length, precision);
	}
}
