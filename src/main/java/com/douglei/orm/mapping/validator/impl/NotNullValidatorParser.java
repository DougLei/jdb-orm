package com.douglei.orm.mapping.validator.impl;

import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.ValidatedData;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidateFailResult;
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
		
		return new Validator(getPriority(), null) {
			@Override
			public ValidateFailResult validate(ValidatedData data) {
				if(data.getValue() == null && !data.isNullable())
					return new ValidateFailResult(data.getName(), "不能为空", "jdb.data.validator.notnull");
				return null;
			}
		};
	}
}
