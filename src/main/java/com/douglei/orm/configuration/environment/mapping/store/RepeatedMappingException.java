package com.douglei.orm.configuration.environment.mapping.store;

/**
 * Mapping重复异常
 * @author DougLei
 */
public class RepeatedMappingException extends RuntimeException{
	private static final long serialVersionUID = -1877710864587394350L;

	public RepeatedMappingException(String message) {
		super(message);
	}
}
