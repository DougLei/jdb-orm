package com.douglei.orm.core.metadata.validator.impl;

import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 是否可为空验证器
 * @author DougLei
 */
public class _NullableValidator extends Validator {
	private static final long serialVersionUID = -6644318115059923918L;
	private boolean nullable;
	
	public _NullableValidator(boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public String getName() {
		return "<_nullable_validator_内部方法>";
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
