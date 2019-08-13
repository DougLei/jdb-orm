package com.douglei.orm.configuration.environment.mapping.store;

/**
 * 不支持的MappingStore异常
 * @author DougLei
 */
public class UnsupportMappingStoreException extends RuntimeException{
	private static final long serialVersionUID = -763737045474402170L;

	public UnsupportMappingStoreException(String message) {
		super(message);
	}
}