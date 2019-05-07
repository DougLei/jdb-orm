package com.douglei.configuration.environment.mapping;

/**
 * Mapping重复异常
 * @author DougLei
 */
public class RepeatMappingCodeException extends RuntimeException{
	private static final long serialVersionUID = 4580162656715333684L;

	public RepeatMappingCodeException() {
		super();
	}

	public RepeatMappingCodeException(String message) {
		super(message);
	}
}
