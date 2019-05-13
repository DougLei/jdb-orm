package com.douglei.configuration.extconfiguration.datatypehandler;

/**
 * DataTypeHandler class转换异常
 * @author DougLei
 */
public class DataTypeHandlerClassCastException extends RuntimeException{
	private static final long serialVersionUID = 6822507199320530600L;

	public DataTypeHandlerClassCastException() {
		super();
	}

	public DataTypeHandlerClassCastException(String message) {
		super(message);
	}
}
