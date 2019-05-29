package com.douglei.database.metadata.table;

/**
 * column重复异常
 * @author DougLei
 */
public class RepeatColumnException extends RuntimeException{
	private static final long serialVersionUID = -6084658655502915089L;

	public RepeatColumnException(String message) {
		super(message);
	}
}
