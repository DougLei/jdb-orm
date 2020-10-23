package com.douglei.orm.mapping.metadata.validator.impl;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 数据类型验证器, 包括验证长度和精度
 * @author DougLei
 */
public class _DataTypeValidator extends Validator {
	
	private DBDataType dbDataType;
	private int length;
	private int precision;
	
	@Override
	protected int getOrder() {
		return 20;
	}

	public _DataTypeValidator(DBDataType dbDataType, int length, int precision) {
		this.dbDataType = dbDataType;
		this.length = length;
		this.precision = precision;
	}

	@Override
	public ValidationResult validate(String name, Object value) {
		return dbDataType.validate(name, value, length, precision);
	}
}
