package com.douglei.orm.sessionfactory.data.validator;

import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 
 * @author DougLei
 */
public class DataValidatorProcessor {

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public ValidatorResult doValidate(Object obj) {
		return doValidate(obj.getClass().getName(), obj);
	}

	/**
	 * 
	 * @param code
	 * @param obj
	 * @return
	 */
	public ValidatorResult doValidate(String code, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
