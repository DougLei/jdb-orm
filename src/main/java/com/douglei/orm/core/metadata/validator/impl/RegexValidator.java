package com.douglei.orm.core.metadata.validator.impl;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.core.metadata.validator.Validator;

/**
 * 正则验证器
 * @author DougLei
 */
public class RegexValidator extends Validator {
	private static final long serialVersionUID = 1902868937312498286L;
	private List<RegexEntity> regexes;
	
	@Override
	protected void doInitial(String validatorConfigValue) {
		if(validatorConfigValue.startsWith("{")) {
			validatorConfigValue = "["+validatorConfigValue+"]";
		}
		regexes = JSONArray.parseArray(validatorConfigValue, RegexEntity.class);
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value) {
		return doValidate(validateFieldName, value.toString());
	}
	
	private ValidationResult doValidate(String validateFieldName, String value) {
		ValidationResult result = null;
		for (RegexEntity regex : regexes) {
			if((result = regex.match(validateFieldName, value)) != null) {
				break;
			}
		}
		return result;
	}
}

/**
 * 正则表达式的结构
 * @author DougLei
 */
class RegexEntity implements Serializable{
	private static final long serialVersionUID = -5532890074831604734L;
	private static final String DEFAULT_MESSAGE = "匹配正则表达式失败";
	private static final String DEFAULT_I18N_CODE = "jdb.data.validator.regex.matching.fail";
	
	private Pattern pattern;
	private String express;
	private boolean multiline;
	private String message;
	private String i18nCode;
	
	public ValidationResult match(String validateFieldName, String value) {
		if(getPattern().matcher(value).matches()) 
			return null;
		return new ValidationResult(validateFieldName, message==null?DEFAULT_MESSAGE:message, i18nCode==null?DEFAULT_I18N_CODE:i18nCode);
	}
	
	private Pattern getPattern() {
		if(pattern == null) {
			if(multiline) {
				pattern = Pattern.compile(express, Pattern.MULTILINE);
			}else {
				pattern = Pattern.compile(express);
			}
		}
		return pattern;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public void setMultiline(boolean multiline) {
		this.multiline = multiline;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}
}
