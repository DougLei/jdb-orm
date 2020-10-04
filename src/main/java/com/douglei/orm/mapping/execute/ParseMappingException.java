package com.douglei.orm.mapping.execute;

/**
 * 解析mapping异常
 * @author DougLei
 */
public class ParseMappingException extends Exception{

	public ParseMappingException(String message, Throwable e) {
		super(message, e);
	}
}
