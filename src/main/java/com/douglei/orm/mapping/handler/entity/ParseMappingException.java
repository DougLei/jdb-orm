package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.handler.MappingHandleException;

/**
 * 解析mapping异常
 * @author DougLei
 */
public class ParseMappingException extends MappingHandleException{
	
	public ParseMappingException(String message) {
		super(message);
	}
	public ParseMappingException(String message, Throwable e) {
		super(message, e);
	}
}
