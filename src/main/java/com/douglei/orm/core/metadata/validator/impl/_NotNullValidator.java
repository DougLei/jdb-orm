package com.douglei.orm.core.metadata.validator.impl;

import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 非空验证
 * @author DougLei
 */
public class _NotNullValidator extends Validator {

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
				public String getI18nCode_() {
					return "notnull";
				}
			};
		}
		return null;
	}
}
