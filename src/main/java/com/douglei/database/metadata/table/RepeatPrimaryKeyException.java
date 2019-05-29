package com.douglei.database.metadata.table;

/**
 * 主键重复异常
 * @author DougLei
 */
public class RepeatPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = 6665595753762759298L;

	public RepeatPrimaryKeyException(String message) {
		super(message);
	}
}
