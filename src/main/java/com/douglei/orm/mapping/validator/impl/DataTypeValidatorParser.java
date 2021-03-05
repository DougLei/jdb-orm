package com.douglei.orm.mapping.validator.impl;

import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.ValidatedData;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidatorParser;

/**
 * 
 * @author DougLei
 */
public class DataTypeValidatorParser implements ValidatorParser {
	
	@Override
	public String getType() {
		return "enableDataType";
	}

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public Validator parse(String value) throws MetadataParseException {
		if("false".equalsIgnoreCase(value))
			return null;
		return DataTypeValidator.getSingleton();
	}
}

/**
 * 
 * @author DougLei
 */
class DataTypeValidator extends Validator {
	private static DataTypeValidator singleton;
	private DataTypeValidator() {
		super(10);
	}
	public static DataTypeValidator getSingleton() {
		if(singleton == null) {
			synchronized (DataTypeValidator.class) {
				if(singleton == null) 
					singleton = new DataTypeValidator();
			}
		}
		return singleton;
	}
	public Object readResolve() {
		return getSingleton();
	}
	
	
	@Override
	public ValidateFailResult validate(ValidatedData data) {
		if(data.getValue() == null)
			return null;
		return data.getDBDataType().validate(data.getName(), data.getValue(), data.getLength(), data.getPrecision());
	}
}