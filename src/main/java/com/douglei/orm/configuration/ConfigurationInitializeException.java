package com.douglei.orm.configuration;

/**
 * 配置初始化异常
 * @author DougLei
 */
public class ConfigurationInitializeException extends RuntimeException{
	private static final long serialVersionUID = 679157047633070619L;

	public ConfigurationInitializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
