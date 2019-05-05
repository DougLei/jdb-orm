package com.douglei.sessions.session;

/**
 * mapping不匹配异常
 * @author DougLei
 */
public class MappingMismatchingException extends RuntimeException{
	private static final long serialVersionUID = 5341716856691528021L;

	public MappingMismatchingException() {
		super();
	}

	public MappingMismatchingException(String message) {
		super(message);
	}
}
