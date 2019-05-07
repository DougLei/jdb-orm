package com.douglei.configuration;

/**
 * 销毁异常
 * @author DougLei
 */
public class DestroyException extends RuntimeException{
	private static final long serialVersionUID = 4209310101243438798L;

	public DestroyException() {
		super();
	}

	public DestroyException(String message) {
		super(message);
	}

	public DestroyException(String message, Throwable cause) {
		super(message, cause);
	}
}
