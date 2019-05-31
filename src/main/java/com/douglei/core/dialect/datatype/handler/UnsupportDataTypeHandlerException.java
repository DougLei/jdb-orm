package com.douglei.core.dialect.datatype.handler;

/**
 * 不支持的DataTypeHandler异常
 * @author DougLei
 */
public class UnsupportDataTypeHandlerException extends RuntimeException{
	private static final long serialVersionUID = -4075567978170554601L;

	public UnsupportDataTypeHandlerException() {
		super();
	}

	public UnsupportDataTypeHandlerException(String message) {
		super(message);
	}
}
