package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.handler.MappingHandlerException;

/**
 * 解析mapping异常
 * @author DougLei
 */
public class ParseMappingException extends MappingHandlerException{
	private static final long serialVersionUID = -8282266997013748439L;
	
	public ParseMappingException(String message) {
		super(message);
	}
	public ParseMappingException(String message, Throwable e) {
		super(message, e);
	}
}
