package com.douglei.orm.core;

/**
 * 所有异常的父类
 * @author DougLei
 */
public class OrmException extends RuntimeException {
	private static final long serialVersionUID = -1352988733235551482L;

	public OrmException() {
		super();
	}

	public OrmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OrmException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrmException(String message) {
		super(message);
	}

	public OrmException(Throwable cause) {
		super(cause);
	}
}
