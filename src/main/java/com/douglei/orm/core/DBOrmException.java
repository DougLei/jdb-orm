package com.douglei.orm.core;

/**
 * 
 * @author DougLei
 */
public class DBOrmException extends RuntimeException {
	private static final long serialVersionUID = -227184058155082196L;

	public DBOrmException() {
		super();
	}

	public DBOrmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBOrmException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBOrmException(String message) {
		super(message);
	}

	public DBOrmException(Throwable cause) {
		super(cause);
	}
}
