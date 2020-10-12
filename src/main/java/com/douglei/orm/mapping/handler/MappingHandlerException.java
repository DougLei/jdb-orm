package com.douglei.orm.mapping.handler;

/**
 * 操作mapping异常
 * @author DougLei
 */
public class MappingHandlerException extends Exception{
	public MappingHandlerException(String message) {
		super(message);
	}
	public MappingHandlerException(String message, Throwable t) {
		super(message, t);
	}
}
