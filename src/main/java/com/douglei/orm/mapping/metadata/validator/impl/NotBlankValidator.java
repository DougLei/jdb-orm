package com.douglei.orm.mapping.metadata.validator.impl;

import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 不能为空字符串验证
 * @author DougLei
 */
public class NotBlankValidator extends Validator{
	private static final long serialVersionUID = 3737012381222822895L;

	@Override
	public int getOrder() {
		return 40;
	}

	@Override
	public ValidationResult validate(String name, Object value) {
		if(value == null || value.toString().trim().length() == 0) 
			return new ValidationResult(name, "不能为空字符串", "jdb.data.validator.notblank");
		return null;
	}
}
