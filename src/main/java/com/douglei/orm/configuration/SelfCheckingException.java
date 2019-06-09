package com.douglei.orm.configuration;

/**
 * 自检异常
 * @author DougLei
 */
public class SelfCheckingException extends RuntimeException{
	private static final long serialVersionUID = -6708133480511898420L;

	public SelfCheckingException() {
		super();
	}

	public SelfCheckingException(String message) {
		super(message);
	}

	public SelfCheckingException(String message, Throwable cause) {
		super(message, cause);
	}
}
