package com.douglei.configuration;

/**
 * 初始化configuration异常
 * @author DougLei
 */
public class ConfigurationInitialException extends RuntimeException{
	private static final long serialVersionUID = 3253062530930515693L;

	public ConfigurationInitialException() {
		super();
	}

	public ConfigurationInitialException(String message) {
		super(message);
	}

	public ConfigurationInitialException(String message, Throwable cause) {
		super(message, cause);
	}
}
