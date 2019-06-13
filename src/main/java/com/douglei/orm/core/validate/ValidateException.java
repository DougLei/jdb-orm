package com.douglei.orm.core.validate;

import com.douglei.orm.context.RunMappingConfigurationContext;

/**
 * 验证异常
 * @author DougLei
 */
public class ValidateException extends RuntimeException{
	private static final long serialVersionUID = -5967863771032590175L;

	public ValidateException(String descriptionName, String name, String message) {
		super(RunMappingConfigurationContext.getCurrentExecuteMappingDescription() + ", " + descriptionName + "["+name+"]" + message);
	}
}
