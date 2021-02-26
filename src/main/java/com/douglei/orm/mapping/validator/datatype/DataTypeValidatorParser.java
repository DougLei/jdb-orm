package com.douglei.orm.mapping.validator.datatype;

import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.ValidatorParser;
import com.douglei.orm.mapping.validator.Validator;

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
	public Validator parse(String value) throws MetadataParseException {
		if(!"false".equalsIgnoreCase(value))
			return DataTypeValidator.singleton;
		return null;
	}
}
