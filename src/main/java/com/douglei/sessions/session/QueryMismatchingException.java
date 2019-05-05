package com.douglei.sessions.session;

/**
 * query不匹配异常
 * @author DougLei
 */
public class QueryMismatchingException extends RuntimeException{
	private static final long serialVersionUID = 1340640734087327823L;

	public QueryMismatchingException() {
		super();
	}

	public QueryMismatchingException(String message) {
		super(message);
	}
}
