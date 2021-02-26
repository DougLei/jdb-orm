package com.douglei.orm.mapping.validator.notnull;

import com.douglei.orm.mapping.validator.Validator;

/**
 * 
 * @author DougLei
 */
public class NotNullValidator implements Validator {
	private NotNullValidator() {}
	
	static final NotNullValidator singleton = new NotNullValidator();
	public Object readResolve() {
		return singleton;
	}
	
	@Override
	public int getPriority() {
		return 0;
	}
}
