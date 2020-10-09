package com.douglei.orm.dialect.datatype.db.impl;

/**
 * 读取数据流异常
 * @author DougLei
 */
public class ReadDataStreamException extends RuntimeException{
	private static final long serialVersionUID = -1949402624851266976L;

	public ReadDataStreamException(String message, Exception t) {
		super(message, t);
	}
}
