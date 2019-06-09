package com.douglei.orm.configuration.environment.mapping.cache.store;

/**
 * Mapping重复异常
 * @author DougLei
 */
public class RepeatedMappingException extends RuntimeException{
	private static final long serialVersionUID = 8443388903343287042L;

	public RepeatedMappingException(String message) {
		super(message);
	}
}
