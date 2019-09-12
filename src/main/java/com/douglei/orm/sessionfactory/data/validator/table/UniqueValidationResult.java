package com.douglei.orm.sessionfactory.data.validator.table;

import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class UniqueValidationResult extends ValidationResult {
	private Object uniqueValue;
	
	public UniqueValidationResult(String validateFieldName, Object uniqueValue) {
		super(validateFieldName);
		this.uniqueValue = uniqueValue;
	}

	@Override
	public String getMessage() {
		return  uniqueValue + " 值不唯一, 已存在相同值的数据";
	}
	
	@Override
	public String getI18nCode() {
		return i18nCodePrefix + "value.violation.unique.constraint";
	}
	
	@Override
	public Object[] getI18nParams() {
		return new Object[] {uniqueValue};
	}
}
