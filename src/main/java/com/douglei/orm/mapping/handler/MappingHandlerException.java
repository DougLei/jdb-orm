package com.douglei.orm.mapping.handler;

import com.douglei.orm.configuration.OrmException;

/**
 * 操作mapping异常
 * @author DougLei
 */
public class MappingHandlerException extends OrmException{
	
	public MappingHandlerException(String message) {
		super(message);
	}
	public MappingHandlerException(String message, Throwable t) {
		super(message, t);
	}
}
