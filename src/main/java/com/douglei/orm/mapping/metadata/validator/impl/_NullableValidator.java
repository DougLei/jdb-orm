package com.douglei.orm.mapping.metadata.validator.impl;

import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 是否可为空验证器
 * @author DougLei
 */
public class _NullableValidator extends Validator {
	private static final long serialVersionUID = -6055777291653897820L;
	
	private boolean nullable;
	public _NullableValidator(boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public int getOrder() {
		return 0;
	}
	
	@Override
	protected boolean toNext(Object value) {
		if(value == null)
			return false;
		return true;
	}

	@Override
	public ValidationResult validate(String name, Object value) {
		if(!nullable && value == null) 
			return new ValidationResult(name, "不能为空", "jdb.data.validator.notnull");
		return null;
	}
}
