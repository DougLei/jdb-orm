package com.douglei.orm.core.metadata.validator;

import com.douglei.orm.core.result.Result;

/**
 * 验证结果对象
 * @author DougLei
 */
public abstract class ValidatorResult extends Result{
	public static final String i18nCodePrefix = "jdb.validator.";
	private String validateFieldName;
	
	public ValidatorResult(String validateFieldName) {
		this.validateFieldName = validateFieldName;
	}
	
	public final String getValidateFieldName() {
		return validateFieldName;
	}
}
