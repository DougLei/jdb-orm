package com.douglei.orm.configuration;

/**
 * 
 * @author DougLei
 */
public class JDBORMException extends RuntimeException {
	private static final long serialVersionUID = -1935871933249528309L;

	public JDBORMException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JDBORMException(String message, Throwable cause) {
		super(message, cause);
	}

	public JDBORMException(String message) {
		super(message);
	}

	public JDBORMException(Throwable cause) {
		super(cause);
	}
}
