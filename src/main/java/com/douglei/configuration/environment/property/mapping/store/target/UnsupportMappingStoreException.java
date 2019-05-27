package com.douglei.configuration.environment.property.mapping.store.target;

/**
 * 不支持的MappingStore异常
 * @author DougLei
 */
public class UnsupportMappingStoreException extends RuntimeException{
	private static final long serialVersionUID = 7128277001109177230L;

	public UnsupportMappingStoreException(String message) {
		super(message);
	}
}
