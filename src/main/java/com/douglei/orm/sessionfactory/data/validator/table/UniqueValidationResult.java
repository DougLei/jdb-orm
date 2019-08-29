package com.douglei.orm.sessionfactory.data.validator.table;

import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public abstract class UniqueValidationResult extends ValidationResult {

	public UniqueValidationResult(String validateFieldName) {
		super(validateFieldName);
	}
}
