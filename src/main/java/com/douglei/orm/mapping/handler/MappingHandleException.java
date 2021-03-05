package com.douglei.orm.mapping.handler;

import com.douglei.orm.configuration.OrmException;

/**
 * 操作mapping异常
 * @author DougLei
 */
public class MappingHandleException extends OrmException{
	
	public MappingHandleException(String message) {
		super(message);
	}
	public MappingHandleException(String message, Throwable t) {
		super(message, t);
	}
}
