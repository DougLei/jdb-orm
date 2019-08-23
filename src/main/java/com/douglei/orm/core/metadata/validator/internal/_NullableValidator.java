package com.douglei.orm.core.metadata.validator.internal;

import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 是否可为空验证器
 * @author DougLei
 */
public class _NullableValidator extends Validator {
	private static final long serialVersionUID = -5939247064758791584L;
	private boolean nullable;
	
	public _NullableValidator(boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public String getName() {
		return null;
	}

	@Override
	public ValidatorResult doValidate(String validateFieldName, Object value) {
		if(!nullable && value == null) {
			return new ValidatorResult(validateFieldName) {
				
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
