package com.douglei.orm.configuration;

/**
 * 
 * @author DougLei
 */
public class OrmException extends RuntimeException {

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
