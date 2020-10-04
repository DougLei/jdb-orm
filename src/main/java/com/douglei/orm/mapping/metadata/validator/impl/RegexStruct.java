package com.douglei.orm.mapping.metadata.validator.impl;

import java.util.regex.Pattern;

import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 正则表达式的结构
 * @author DougLei
 */
class RegexStruct {
	private static final String DEFAULT_MESSAGE = "匹配正则表达式失败";
	private static final String DEFAULT_I18N_CODE = "jdb.data.validator.regex.matching.fail";
	
	private String express;
	private Pattern pattern;
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
