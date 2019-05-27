package com.douglei.database.dialect.datatype.handler;

/**
 * 重复的DataTypeHandler异常
 * @author DougLei
 */
public class RepeatedDataTypeHandlerException extends RuntimeException{
	private static final long serialVersionUID = -634116904265559599L;

	public RepeatedDataTypeHandlerException(String message) {
		super(message);
	}
}
