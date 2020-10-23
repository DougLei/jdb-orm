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
	private static final long serialVersionUID = -7459993719344601539L;
	
	private List<RegexStruct> regexes;
	
	@Override
	public int getOrder() {
		return 60;
	}
	
	@Override
	protected void init_(String configValue) {
		if(configValue.startsWith("{")) 
			configValue = "["+configValue+"]";
		regexes = JSONArray.parseArray(configValue, RegexStruct.class);
	}
	
	@Override
	public ValidationResult validate(String name, Object value) {
		return validate_(name, value.toString());
	}
	
	private ValidationResult validate_(String name, String value) {
		ValidationResult result = null;
		for (RegexStruct regex : regexes) {
			if((result = regex.match(name, value)) != null) 
				break;
		}
		return result;
	}
}