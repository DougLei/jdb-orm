package com.douglei.orm.dialect;

/**
 * 不支持的Dialect异常
 * @author DougLei
 */
public class UnsupportDialectException extends RuntimeException{

	public UnsupportDialectException(String message) {
		super(message);
	}
}
