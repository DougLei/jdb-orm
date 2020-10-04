package com.douglei.orm.mapping.impl.table.exception;

/**
 * 主键重复异常
 * @author DougLei
 */
public class RepeatedPrimaryKeyException extends RuntimeException{

	public RepeatedPrimaryKeyException(String message) {
		super(message);
	}
}
