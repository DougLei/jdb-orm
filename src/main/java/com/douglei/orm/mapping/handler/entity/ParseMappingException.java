package com.douglei.orm.mapping.handler.entity;

/**
 * 解析mapping异常
 * @author DougLei
 */
public class ParseMappingException extends Exception{

	public ParseMappingException(String message, Throwable e) {
		super(message, e);
	}
}
