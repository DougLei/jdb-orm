package com.douglei.orm.mapping.validator.notnull;

import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.ValidatorParser;
import com.douglei.orm.mapping.validator.Validator;

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
	public Validator parse(String value) throws MetadataParseException {
		if(!"false".equalsIgnoreCase(value))
			return NotNullValidator.singleton;
		return null;
	}
}
