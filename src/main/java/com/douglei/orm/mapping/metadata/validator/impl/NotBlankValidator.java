package com.douglei.orm.mapping.metadata.validator.impl;

import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 不能为空字符串验证
 * @author DougLei
 */
public class NotBlankValidator extends Validator{
	private static final long serialVersionUID = 2600847895450553854L;

	@Override
	public ValidationResult validate(String fieldName, Object value) {
		if(value == null || value.toString().trim().length() == 0) 
			return new ValidationResult(fieldName, "不能为空字符串", "jdb.data.validator.notblank");
		return null;
	}
}
