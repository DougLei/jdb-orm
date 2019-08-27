package com.douglei.orm.sessionfactory.data.validator;

import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public interface DataValidator {
	
	ValidationResult doValidate(Object object);
}
