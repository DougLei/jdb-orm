package com.douglei.orm.sessionfactory.sessions;

/**
 * 执行异常
 * @author DougLei
 */
public class ExecutionException extends RuntimeException {
	
	public ExecutionException(String message) {
		super(message);
	}
	public ExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
