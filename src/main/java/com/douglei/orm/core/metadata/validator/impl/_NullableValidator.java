package com.douglei.orm.core.metadata.validator.impl;

import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 是否可为空验证器
 * @author DougLei
 */
public class _NullableValidator extends Validator {
	private static final long serialVersionUID = -5894775458262230720L;
	private boolean nullable;
	
	public _NullableValidator(String validatorConfigValue) {
		super(validatorConfigValue);
	}
	
	@Override
	protected void doInitial(String validatorConfigValue) {
		nullable = Boolean.parseBoolean(validatorConfigValue);
	}

	@Override
	public String getName() {
		return "<_nullable_内部方法>";
	}

	@Override
	public ValidatorResult doValidate(Object value) {
		if(!nullable && value == null) {
			return new ValidatorResult() {
				
				@Override
				public String getMessage() {
					return "不能为空";
				}
				
				@Override
				public String getI18nCode() {
					return i18nCodePrefix + "nullable.notnull";
				}
			};
		}
		return null;
	}
}
