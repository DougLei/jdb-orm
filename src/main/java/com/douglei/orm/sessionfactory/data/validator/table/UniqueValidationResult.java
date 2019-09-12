package com.douglei.orm.sessionfactory.data.validator.table;

import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class UniqueValidationResult extends ValidationResult {
	
	public UniqueValidationResult(String validateFieldName) {
		super(validateFieldName);
	}

	@Override
	public String getMessage() {
		return  "值不唯一, 已存在相同值的数据";
	}
	
	@Override
	public String getI18nCode() {
		return i18nCodePrefix + "value.violation.unique.constraint";
	}
}
