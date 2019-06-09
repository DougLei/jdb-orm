package com.douglei.orm.core.dialect;

/**
 * Dialect实例重复异常
 * @author DougLei
 */
public class RepeatedDialectException extends RuntimeException{
	private static final long serialVersionUID = -2044977391577980593L;

	public RepeatedDialectException(String message) {
		super(message);
	}
}
