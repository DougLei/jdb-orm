package com.douglei.orm.mapping.validator.notblank;

import com.douglei.orm.mapping.validator.Validator;

/**
 * 
 * @author DougLei
 */
public class NotBlankValidator implements Validator {
	private NotBlankValidator() {}
	
	static final NotBlankValidator singleton = new NotBlankValidator();
	public Object readResolve() {
		return singleton;
	}
	
	@Override
	public int getPriority() {
		return 20;
	}
}
