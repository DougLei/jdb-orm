package com.douglei.orm.mapping.impl.table.exception;

/**
 * 主键重复异常
 * @author DougLei
 */
public class RepeatedPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = 7019926461561603612L;

	public RepeatedPrimaryKeyException(String message) {
		super(message);
	}
}
