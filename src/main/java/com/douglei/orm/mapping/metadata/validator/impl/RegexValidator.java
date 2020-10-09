package com.douglei.orm.mapping.metadata.validator.impl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.Validator;

/**
 * 正则验证器
 * @author DougLei
 */
public class RegexValidator extends Validator {
	private List<RegexStruct> regexes;
	
	@Override
	protected void initial(String configValue) {
		if(configValue.startsWith("{")) 
			configValue = "["+configValue+"]";
		regexes = JSONArray.parseArray(configValue, RegexStruct.class);
	}
	
	@Override
	public ValidationResult validate(String fieldName, Object value) {
		return validate_(fieldName, value.toString());
	}
	
	private ValidationResult validate_(String validateFieldName, String value) {
		ValidationResult result = null;
		for (RegexStruct regex : regexes) {
			if((result = regex.match(validateFieldName, value)) != null) {
				break;
			}
		}
		return result;
	}
}