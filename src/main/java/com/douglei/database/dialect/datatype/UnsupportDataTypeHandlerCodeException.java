package com.douglei.database.dialect.datatype;

/**
 * 不支持的DataTypeHandler异常
 * @author DougLei
 */
public class UnsupportDataTypeHandlerCodeException extends RuntimeException{
	private static final long serialVersionUID = 3645065043136634637L;

	public UnsupportDataTypeHandlerCodeException() {
		super();
	}

	public UnsupportDataTypeHandlerCodeException(String message) {
		super(message);
	}
}
