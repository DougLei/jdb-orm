package com.douglei.orm.sessionfactory.sessions.session.table.impl;

import com.douglei.orm.core.metadata.validator.DataValidationException;
import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 重复的唯一值异常
 * @author DougLei
 */
public class RepeatedUniqueValueException extends DataValidationException{
	private static final long serialVersionUID = 6287409018254603339L;

	public RepeatedUniqueValueException(String descriptionName, String name, Object value, ValidationResult validatorResult) {
		super(descriptionName, name, value, validatorResult);
	}
}
