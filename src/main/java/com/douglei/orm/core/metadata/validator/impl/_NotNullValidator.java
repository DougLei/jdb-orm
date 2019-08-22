package com.douglei.orm.core.metadata.validator.impl;

import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 非空验证器
 * @author DougLei
 */
public class _NotNullValidator extends Validator {
	private static final long serialVersionUID = -4082522870619949087L;

	@Override
	public String getName() {
		return "_notnull";
	}

	@Override
	public ValidatorResult doValidate(Object value) {
		if(value == null) {
			return new ValidatorResult() {
				
				@Override
				public String getMessage() {
					return "不能为空";
				}
				
				@Override
				public String getI18nCode() {
					return i18nCodePrefix + "notnull";
				}
			};
		}
		return null;
	}
}
