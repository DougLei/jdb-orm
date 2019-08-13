package com.douglei.orm.configuration.environment.mapping.cache.store;

/**
 * 不支持的MappingStore异常
 * @author DougLei
 */
public class UnsupportMappingStoreException extends RuntimeException{
	private static final long serialVersionUID = 387411026211363646L;

	public UnsupportMappingStoreException(String message) {
		super(message);
	}
}
