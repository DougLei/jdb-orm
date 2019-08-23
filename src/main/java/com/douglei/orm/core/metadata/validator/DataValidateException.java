package com.douglei.orm.core.metadata.validator;

import com.douglei.orm.context.ExecMappingDescriptionContext;

/**
 * 
 * @author DougLei
 */
public class DataValidateException extends RuntimeException{
	private static final long serialVersionUID = -1963432178293425219L;
	
	private ValidatorResult validatorResult;
	
	public DataValidateException(String descriptionName, String name, Object value, ValidatorResult validatorResult) {
		super(ExecMappingDescriptionContext.getExecMappingDescription() + ", " + descriptionName + "["+name+"], 传入的值为[" + value + "], " + validatorResult.getMessage());
		this.validatorResult = validatorResult;
	}

	public ValidatorResult getValidatorResult() {
		return validatorResult;
	}
}
