package com.douglei.orm.mapping.handler;

/**
 * 操作mapping异常
 * @author DougLei
 */
public class MappingHandlerException extends Exception{
	private static final long serialVersionUID = -4386252126785504085L;

	public MappingHandlerException(String message, Throwable t) {
		super(message, t);
	}
}
