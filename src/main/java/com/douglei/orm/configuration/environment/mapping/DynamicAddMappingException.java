package com.douglei.orm.configuration.environment.mapping;

/**
 * 动态添加映射异常
 * @author DougLei
 */
public class DynamicAddMappingException extends Exception{
	private static final long serialVersionUID = -3504172059478482055L;

	public DynamicAddMappingException() {
		super();
	}

	public DynamicAddMappingException(String message) {
		super(message);
	}

	public DynamicAddMappingException(String message, Throwable cause) {
		super(message, cause);
	}
}
