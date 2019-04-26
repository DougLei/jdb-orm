package com.douglei.database.dialect;

/**
 * dialect重复异常
 * @author DougLei
 */
public class RepeatDialectException extends RuntimeException{
	private static final long serialVersionUID = -2759215917472169558L;

	public RepeatDialectException() {
		super();
	}

	public RepeatDialectException(String message) {
		super(message);
	}
}
