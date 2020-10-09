package com.douglei.orm.dialect.temp.datatype.handler;

/**
 * 重复的DataTypeHandler异常
 * @author DougLei
 */
public class RepeatedDataTypeHandlerException extends RuntimeException{

	public RepeatedDataTypeHandlerException(String message) {
		super(message);
	}
}