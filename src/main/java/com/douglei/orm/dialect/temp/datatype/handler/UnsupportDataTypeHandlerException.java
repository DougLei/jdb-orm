package com.douglei.orm.dialect.temp.datatype.handler;

/**
 * 不支持的DataTypeHandler异常
 * @author DougLei
 */
public class UnsupportDataTypeHandlerException extends RuntimeException{

	public UnsupportDataTypeHandlerException() {
		super();
	}

	public UnsupportDataTypeHandlerException(String message) {
		super(message);
	}
}
