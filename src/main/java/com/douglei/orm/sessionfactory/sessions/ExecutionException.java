package com.douglei.orm.sessionfactory.sessions;

/**
 * 执行异常
 * @author DougLei
 */
public class ExecutionException extends RuntimeException {
	private static final long serialVersionUID = -1375667180941960582L;
	
	public ExecutionException(String message) {
		super(message);
	}
	public ExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
