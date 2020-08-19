package com.douglei.orm.core.metadata.validator.internal;

import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.core.metadata.validator.Validator;

/**
 * 是否可为空验证器
 * @author DougLei
 */
public class _NullableValidator extends Validator {
	private static final long serialVersionUID = -631515537317711665L;
	private boolean nullable;
	
	public _NullableValidator(boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value) {
		if(!nullable && value == null) 
			return new ValidationResult(validateFieldName, "不能为空", "jdb.data.validator.notnull");
		return null;
	}
}
