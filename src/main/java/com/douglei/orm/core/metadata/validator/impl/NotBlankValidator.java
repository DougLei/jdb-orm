package com.douglei.orm.core.metadata.validator.impl;

import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;
import com.douglei.tools.utils.StringUtil;

/**
 * 不能为空字符串验证
 * @author DougLei
 */
public class NotBlankValidator extends Validator{
	private static final long serialVersionUID = 4477656770129913047L;

	@Override
	public String getName() {
		return "notblank";
	}

	@Override
	public ValidatorResult doValidate(Object value) {
		if(StringUtil.isEmpty(value)) {
			return new ValidatorResult() {
				
				@Override
				public String getMessage() {
					return "不能为空字符串";
				}
				
				@Override
				protected String getI18nCode() {
					return i18nCodePrefix + "notblank";
				}
			};
		}
		return null;
	}

}
