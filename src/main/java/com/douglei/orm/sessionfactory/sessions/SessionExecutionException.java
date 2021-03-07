package com.douglei.orm.sessionfactory.sessions;

import com.douglei.orm.configuration.OrmException;

/**
 * session执行异常
 * @author DougLei
 */
public class SessionExecutionException extends OrmException {
	
	public SessionExecutionException(String message) {
		super(message);
	}
	public SessionExecutionException(String exceptionDescription, Throwable t) {
		super(exceptionDescription + ", " + t.getMessage(), t);
	}
}
