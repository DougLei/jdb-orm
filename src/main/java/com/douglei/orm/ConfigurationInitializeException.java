package com.douglei.orm;

/**
 * 配置初始化异常
 * @author DougLei
 */
public class ConfigurationInitializeException extends RuntimeException{

	public ConfigurationInitializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
