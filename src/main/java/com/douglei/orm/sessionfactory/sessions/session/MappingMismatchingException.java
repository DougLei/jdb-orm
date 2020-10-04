package com.douglei.orm.sessionfactory.sessions.session;

/**
 * mapping不匹配异常
 * @author DougLei
 */
public class MappingMismatchingException extends RuntimeException{

	public MappingMismatchingException() {
		super();
	}

	public MappingMismatchingException(String message) {
		super(message);
	}
}
