package com.douglei.orm.configuration.environment.mapping;

/**
 * 动态添加映射异常
 * @author DougLei
 */
public class DynamicAddMappingException extends RuntimeException{
	private static final long serialVersionUID = -594738527448783326L;

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
