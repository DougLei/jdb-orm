package com.douglei.orm.core.metadata.validator;

/**
 * 
 * @author DougLei
 */
public class DataValidationException extends RuntimeException{
	private static final long serialVersionUID = -2615923567691590013L;
	private ValidationResult validatorResult;
	
	public DataValidationException(String descriptionName, String name, Object value, ValidationResult validatorResult) {
		super(descriptionName + "["+name+"], 传入的值为[" + value + "], " + validatorResult.getMessage());
		this.validatorResult = validatorResult;
	}

	public ValidationResult getValidatorResult() {
		return validatorResult;
	}
}
