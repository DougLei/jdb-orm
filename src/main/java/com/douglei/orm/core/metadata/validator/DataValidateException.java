package com.douglei.orm.core.metadata.validator;

import com.douglei.orm.context.ExecMappingDescriptionContext;

/**
 * 
 * @author DougLei
 */
public class DataValidateException extends RuntimeException{
	private static final long serialVersionUID = 5605037050016800989L;
	private ValidationResult validatorResult;
	
	public DataValidateException(String descriptionName, String name, Object value, ValidationResult validatorResult) {
		super(ExecMappingDescriptionContext.getExecMappingDescription() + ", " + descriptionName + "["+name+"], 传入的值为[" + value + "], " + validatorResult.getMessage());
		this.validatorResult = validatorResult;
	}

	public ValidationResult getValidatorResult() {
		return validatorResult;
	}
}
