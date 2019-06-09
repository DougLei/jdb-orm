package com.douglei.orm.configuration.environment.mapping.cache.store;

/**
 * 不存在mapping异常
 * @author DougLei
 */
public class NotExistsMappingException extends RuntimeException{
	private static final long serialVersionUID = -5923083439698538157L;

	public NotExistsMappingException(String message) {
		super(message);
	}
}
