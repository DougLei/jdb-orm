package com.douglei.orm.mapping.metadata.validator;

/**
 * 
 * @author DougLei
 */
public class DataValidationException extends RuntimeException{
	private ValidationResult validatorResult;
	
	public DataValidationException(String descriptionName, String name, Object value, ValidationResult validatorResult) {
		super(descriptionName + "["+name+"], 传入的值为[" + value + "], " + validatorResult.getOriginMessage());
		this.validatorResult = validatorResult;
	}

	public ValidationResult getValidatorResult() {
		return validatorResult;
	}
}
