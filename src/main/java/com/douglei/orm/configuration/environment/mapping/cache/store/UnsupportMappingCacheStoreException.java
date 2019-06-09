package com.douglei.orm.configuration.environment.mapping.cache.store;

/**
 * 不支持的MappingCacheStore异常
 * @author DougLei
 */
public class UnsupportMappingCacheStoreException extends RuntimeException{
	private static final long serialVersionUID = -3102502299035527181L;

	public UnsupportMappingCacheStoreException(String message) {
		super(message);
	}
}
