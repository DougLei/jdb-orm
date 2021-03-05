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
public class NotNullValidatorParser implements ValidatorParser {
	
	@Override
	public String getType() {
		return "enableNotNull";
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public Validator parse(String value) throws MetadataParseException {
		if("false".equalsIgnoreCase(value))
			return null;
		return NotNullValidator.getSingleton();
	}
}

/**
 * 
 * @author DougLei
 */
class NotNullValidator extends Validator {
	private static NotNullValidator singleton;
	private NotNullValidator() {
		super(0);
	}
	public static NotNullValidator getSingleton() {
		if(singleton == null) {
			synchronized (NotNullValidator.class) {
				if(singleton == null) 
					singleton = new NotNullValidator();
			}
		}
		return singleton;
	}
	public Object readResolve() {
		return getSingleton();
	}
	
	@Override
	public ValidateFailResult validate(ValidatedData data) {
		if(data.getValue() == null && !data.isNullable())
			return new ValidateFailResult(data.getName(), "不能为空", "jdb.data.validator.notnull");
		return null;
	}
}
