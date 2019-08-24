package com.douglei.orm.factory.sessions;

/**
 * session执行异常
 * @author DougLei
 */
public class SessionExecutionException extends ExecutionException {
	private static final long serialVersionUID = 7036697756281079652L;
	
	public SessionExecutionException(String message) {
		super(message);
	}
	public SessionExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
