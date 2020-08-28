package com.douglei.orm.configuration.environment.mapping;

/**
 * 解析mapping异常
 * @author DougLei
 */
public class ParseMappingException extends Exception{
	private static final long serialVersionUID = -3305376257417400833L;

	public ParseMappingException(String message, Throwable e) {
		super(message, e);
	}
}
