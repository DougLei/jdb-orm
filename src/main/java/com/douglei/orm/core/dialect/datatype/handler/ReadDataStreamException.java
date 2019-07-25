package com.douglei.orm.core.dialect.datatype.handler;

/**
 * 读取数据流异常
 * @author DougLei
 */
public class ReadDataStreamException extends RuntimeException{
	private static final long serialVersionUID = -4893663584250314170L;

	public ReadDataStreamException(String message, Exception t) {
		super(message, t);
	}
}
