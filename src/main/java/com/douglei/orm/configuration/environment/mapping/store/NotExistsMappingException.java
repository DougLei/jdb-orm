package com.douglei.orm.configuration.environment.mapping.store;

/**
 * 不存在mapping异常
 * @author DougLei
 */
public class NotExistsMappingException extends RuntimeException{
	private static final long serialVersionUID = -8608282837563316698L;

	public NotExistsMappingException(String message) {
		super(message);
	}
}
