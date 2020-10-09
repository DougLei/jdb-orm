package com.douglei.orm;

/**
 * 配置初始化异常
 * @author DougLei
 */
public class ConfigurationInitializeException extends RuntimeException{
	private static final long serialVersionUID = 123695945522829001L;

	public ConfigurationInitializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
