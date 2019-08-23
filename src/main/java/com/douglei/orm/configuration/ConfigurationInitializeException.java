package com.douglei.orm.configuration;

import com.douglei.orm.core.metadata.validator.DataValidateException;

/**
 * 配置初始化异常
 * @author DougLei
 */
public class ConfigurationInitializeException extends RuntimeException{
	private static final long serialVersionUID = 6898051437420810674L;
	
	public ConfigurationInitializeException(Throwable cause) {
		super(cause);
	}
	public ConfigurationInitializeException(String message, Throwable cause) {
		super(message, cause);
	}
	
//	if(validate) {
//		if(!nullable && value == null) {
//			throw new DataValidateException(descriptionName, name, "不能为空");
//		}
//		if(value != null) {
//			String result = dataType.doValidate(value, length, precision);
//			if(result != null) {
//				throw new DataValidateException(descriptionName, name, result);
//			}
//		}
//	}
}
