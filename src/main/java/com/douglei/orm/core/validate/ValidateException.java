package com.douglei.orm.core.validate;

import com.douglei.orm.context.RunMappingConfigurationContext;

/**
 * 验证异常
 * @author DougLei
 */
public class ValidateException extends RuntimeException{
	private static final long serialVersionUID = 2842458358584692714L;
	
	public ValidateException(String descriptionName, String name, String message) {
		super(RunMappingConfigurationContext.getCurrentExecuteMappingDescription() + ", " + descriptionName + "["+name+"]" + message);
	}
	public ValidateException(String descriptionName, String name, Object value, String message) {
		super(RunMappingConfigurationContext.getCurrentExecuteMappingDescription() + ", " + descriptionName + "["+name+"], 传入的值为[" + value + "], " + message);
	}
}
