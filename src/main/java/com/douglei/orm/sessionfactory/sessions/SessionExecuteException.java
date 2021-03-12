package com.douglei.orm.sessionfactory.sessions;

import com.douglei.orm.configuration.OrmException;

/**
 * session执行异常
 * @author DougLei
 */
public class SessionExecuteException extends OrmException {
	
	public SessionExecuteException(String message) {
		super(message);
	}
	public SessionExecuteException(String exceptionDescription, Throwable t) {
		super(exceptionDescription + ", " + t.getMessage(), t);
	}
}
