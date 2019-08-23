package com.douglei.orm.configuration;

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
}
