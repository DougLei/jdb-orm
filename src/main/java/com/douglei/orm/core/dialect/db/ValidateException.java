package com.douglei.orm.core.dialect.db;

import com.douglei.orm.context.RunMappingConfigurationContext;

/**
 * 验证异常
 * @author DougLei
 */
public class ValidateException extends RuntimeException{

	public ValidateException(String descriptionName, String name, String message) {
		super(RunMappingConfigurationContext.getCurrentExecuteMappingDescription() + ", " + descriptionName + "["+name+"]" + message);
	}
}
