package com.douglei.orm.configuration.impl.element.environment.mapping.table.exception;

/**
 * 主键重复异常
 * @author DougLei
 */
public class RepeatedPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = 4765426865437842699L;

	public RepeatedPrimaryKeyException(String message) {
		super(message);
	}
}
