package com.douglei.database.datatype;

/**
 * DataTypeHandler实例重复异常
 * @author DougLei
 */
public class RepeatDataTypeHandlerException extends RuntimeException{
	private static final long serialVersionUID = 3811753156820387823L;

	public RepeatDataTypeHandlerException() {
		super();
	}

	public RepeatDataTypeHandlerException(String message) {
		super(message);
	}
}
