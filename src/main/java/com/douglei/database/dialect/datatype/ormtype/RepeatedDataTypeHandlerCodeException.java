package com.douglei.database.dialect.datatype.ormtype;

/**
 * 重复的DataTypeHandler code异常
 * @author DougLei
 */
public class RepeatedDataTypeHandlerCodeException extends RuntimeException{
	private static final long serialVersionUID = 4880620463472097740L;

	public RepeatedDataTypeHandlerCodeException() {
		super();
	}

	public RepeatedDataTypeHandlerCodeException(String message) {
		super(message);
	}
}
