package com.douglei.orm.core.metadata.validator.internal;

import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 是否可为空验证器
 * @author DougLei
 */
public class _NullableValidator extends Validator {
	private static final long serialVersionUID = -6495482852688490519L;
	private boolean nullable;
	
	public _NullableValidator(boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public String getName() {
		return null;
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
