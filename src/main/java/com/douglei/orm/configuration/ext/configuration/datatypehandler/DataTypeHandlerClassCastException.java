package com.douglei.orm.configuration.ext.configuration.datatypehandler;

/**
 * DataTypeHandler class转换异常
 * @author DougLei
 */
public class DataTypeHandlerClassCastException extends RuntimeException{
	private static final long serialVersionUID = -5491616186097883788L;

	public DataTypeHandlerClassCastException(String message) {
		super(message);
	}
}
