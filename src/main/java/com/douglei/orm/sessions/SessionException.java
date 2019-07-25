package com.douglei.orm.sessions;

/**
 * session异常
 * @author DougLei
 */
public class SessionException extends RuntimeException{
	private static final long serialVersionUID = -1727843359791307040L;

	public SessionException(String message, Throwable t) {
		super(message, t);
	}
}
