package com.douglei.configuration.environment.mapping;

/**
 * Mapping重复异常
 * @author DougLei
 */
public class RepeatMappingException extends RuntimeException{
	private static final long serialVersionUID = 2588929697905672615L;

	public RepeatMappingException(String message) {
		super(message);
	}
}
