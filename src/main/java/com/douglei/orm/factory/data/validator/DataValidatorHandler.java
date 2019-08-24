package com.douglei.orm.factory.data.validator;

import java.util.Map;

import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 
 * @author DougLei
 */
public interface DataValidatorHandler {
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	ValidatorResult doValidate(Object obj);
	
	/**
	 * 
	 * @param code
	 * @param obj
	 * @return
	 */
	ValidatorResult doValidate(String code, Map<String, Object> obj);
	
	/**
	 * 
	 * @param namespace
	 * @param name
	 * @return
	 */
	ValidatorResult doValidate(String namespace, String name);
	
	/**
	 * 
	 * @param namespace
	 * @param name
	 * @param obj
	 * @return
	 */
	ValidatorResult doValidate(String namespace, String name, Object obj);
}
