package com.douglei.orm.mapping.validator.datatype;

import com.douglei.orm.mapping.validator.Validator;

/**
 * 
 * @author DougLei
 */
public class DataTypeValidator implements Validator {
	private DataTypeValidator() {}
	
	static final DataTypeValidator singleton = new DataTypeValidator();
	public Object readResolve() {
		return singleton;
	}
	
	@Override
	public int getPriority() {
		return 10;
	}
	
}
