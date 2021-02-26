package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.handler.MappingHandlerException;

/**
 * 解析mapping异常
 * @author DougLei
 */
public class ParseMappingException extends MappingHandlerException{
	
	public ParseMappingException(String message) {
		super(message);
	}
	public ParseMappingException(String message, Throwable e) {
		super(message, e);
	}
}
