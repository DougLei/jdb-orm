package com.douglei.orm.sessionfactory.sessions;

import com.douglei.orm.configuration.OrmException;

/**
 * 执行异常
 * @author DougLei
 */
public class ExecutionException extends OrmException {
	
	public ExecutionException(String message) {
		super(message);
	}
	public ExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
