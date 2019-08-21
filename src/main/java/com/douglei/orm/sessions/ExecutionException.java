package com.douglei.orm.sessions;

/**
 * 执行异常
 * @author DougLei
 */
public class ExecutionException extends RuntimeException {
	private static final long serialVersionUID = -2463626586355970375L;
	
	public ExecutionException(String message) {
		super(message);
	}
	public ExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
