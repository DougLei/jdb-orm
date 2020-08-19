package com.douglei.orm.core.metadata.validator.impl;

import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.core.metadata.validator.Validator;

/**
 * 不能为空字符串验证
 * @author DougLei
 */
public class NotBlankValidator extends Validator{
	private static final long serialVersionUID = -2268961920747527794L;

	@Override
	public ValidationResult doValidate(String validateFieldName, Object value) {
		if(value == null || value.toString().trim().length() == 0) 
			return new ValidationResult(validateFieldName, "不能为空字符串", "jdb.data.validator.notblank");
		return null;
	}
}
