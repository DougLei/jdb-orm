package com.douglei.orm.mapping.handler.entity;

/**
 * 解析mapping异常
 * @author DougLei
 */
public class ParseMappingException extends Exception{
	private static final long serialVersionUID = -108732096771187166L;

	public ParseMappingException(String message, Throwable e) {
		super(message, e);
	}
}
