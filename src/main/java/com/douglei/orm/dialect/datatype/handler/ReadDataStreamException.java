package com.douglei.orm.dialect.datatype.handler;

/**
 * 读取数据流异常
 * @author DougLei
 */
public class ReadDataStreamException extends RuntimeException{

	public ReadDataStreamException(String message, Exception t) {
		super(message, t);
	}
}
