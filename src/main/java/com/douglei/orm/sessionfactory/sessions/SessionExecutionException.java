package com.douglei.orm.sessionfactory.sessions;

/**
 * session执行异常
 * @author DougLei
 */
public class SessionExecutionException extends ExecutionException {
	public SessionExecutionException(String message) {
		super(message);
	}
	public SessionExecutionException(String exceptionDescription, Throwable t) {
		super(exceptionDescription + ", " + t.getMessage(), t);
	}
}
