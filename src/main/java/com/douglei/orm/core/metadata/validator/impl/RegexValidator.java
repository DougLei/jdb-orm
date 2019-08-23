package com.douglei.orm.core.metadata.validator.impl;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.douglei.orm.core.metadata.validator.Validator;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 正则验证器
 * @author DougLei
 */
public class RegexValidator extends Validator {
	private static final long serialVersionUID = -6788243515396774377L;
	private List<RegexEntity> regexes;
	
	@Override
	protected void doInitial(String validatorConfigValue) {
		if(validatorConfigValue.startsWith("{")) {
			validatorConfigValue = "["+validatorConfigValue+"]";
		}
		regexes = JSONArray.parseArray(validatorConfigValue, RegexEntity.class);
	}

	@Override
	public String getName() {
		return "regex";
	}

	@Override
	public ValidatorResult doValidate(String validateFieldName, Object value) {
		return doValidate(validateFieldName, value.toString());
	}
	
	private ValidatorResult doValidate(String validateFieldName, String value) {
		ValidatorResult result = null;
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
	private static final long serialVersionUID = -6411684647723481525L;
	private static final String DEFAULT_MESSAGE = "正则表达式匹配失败";
	private static final String DEFAULT_I18N_CODE = ValidatorResult.i18nCodePrefix + "regex.matching.fail";
	
	private Pattern pattern;
	private String express;
	private boolean multiline;
	private String message = DEFAULT_MESSAGE;
	private String i18nCode = DEFAULT_I18N_CODE;
	
	public ValidatorResult match(String validateFieldName, String value) {
		if(getPattern().matcher(value).matches()) {
			return null;
		}
		return new ValidatorResult(validateFieldName) {
			
			@Override
			public String getMessage() {
				return message;
			}
			
			@Override
			protected String getI18nCode() {
				return i18nCode;
			}
		};
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