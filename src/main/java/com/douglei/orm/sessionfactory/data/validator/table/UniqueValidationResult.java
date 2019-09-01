package com.douglei.orm.sessionfactory.data.validator.table;

import java.util.List;

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
	public UniqueValidationResult(List<String> validateFieldNames, Object uniqueValue) {
		super(validateFieldNames);
		this.uniqueValue = uniqueValue;
	}

	@Override
	public String getMessage() {
		return uniqueValue + " 值不唯一, 和之上提交的数据有重复";
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
