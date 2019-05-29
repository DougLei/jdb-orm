package com.douglei.configuration.environment.property.mapping.store.target;

/**
 * 不支持的MappingCacheStore异常
 * @author DougLei
 */
public class UnsupportMappingCacheStoreException extends RuntimeException{
	private static final long serialVersionUID = -4131274377126786075L;

	public UnsupportMappingCacheStoreException(String message) {
		super(message);
	}
}
