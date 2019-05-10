package com.douglei.database.dialect.datatype;

/**
 * 不支持的DataTypeHandler异常
 * @author DougLei
 */
public class UnSupportDataTypeHandlerException extends RuntimeException{
	private static final long serialVersionUID = -9098920006055463677L;

	public UnSupportDataTypeHandlerException() {
		super();
	}

	public UnSupportDataTypeHandlerException(String message) {
		super(message);
	}
}
