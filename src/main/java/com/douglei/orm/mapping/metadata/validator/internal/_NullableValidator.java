package com.douglei.orm.mapping.metadata.validator.internal;

import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 是否可为空验证器
 * @author DougLei
 */
public class _NullableValidator extends Validator {
	private boolean nullable;
	
	public _NullableValidator(boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public ValidationResult validate(String fieldName, Object value) {
		if(!nullable && value == null) 
			return new ValidationResult(fieldName, "不能为空", "jdb.data.validator.notnull");
		return null;
	}
}
