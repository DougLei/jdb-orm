package com.douglei.orm.sessionfactory.sessions.session;

/**
 * mapping不匹配异常
 * @author DougLei
 */
public class MappingMismatchingException extends RuntimeException{
	private static final long serialVersionUID = -6269686812173752450L;

	public MappingMismatchingException() {
		super();
	}

	public MappingMismatchingException(String message) {
		super(message);
	}
}
