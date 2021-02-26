package com.douglei.orm.mapping.validator.notblank;

import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.ValidatorParser;
import com.douglei.orm.mapping.validator.Validator;

/**
 * 
 * @author DougLei
 */
public class NotBlankValidatorParser implements ValidatorParser {

	@Override
	public String getType() {
		return "enableNotBlank";
	}
	
	@Override
	public Validator parse(String value) throws MetadataParseException {
		if(!"false".equalsIgnoreCase(value))
			return NotBlankValidator.singleton;
		return null;
	}
}
