package com.douglei.orm.core.dialect;

/**
 * 不支持的Dialect异常
 * @author DougLei
 */
public class UnsupportDialectException extends RuntimeException{
	private static final long serialVersionUID = 392973762348852188L;

	public UnsupportDialectException(String message) {
		super(message);
	}
}
