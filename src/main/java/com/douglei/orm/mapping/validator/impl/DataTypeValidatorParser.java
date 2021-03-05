package com.douglei.orm.mapping.validator.impl;

import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.ValidatedData;
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
			
		return new Validator(getPriority(), null) {
			@Override
			public ValidateFailResult validate(ValidatedData data) {
				if(data.getValue() == null)
					return null;
				return data.getDBDataType().validate(data.getName(), data.getValue(), data.getLength(), data.getPrecision());
			}
		};
	}
}