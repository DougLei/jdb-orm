package com.douglei.orm.sessionfactory.sessions.sqlsession;

import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;

/**
 * 
 * @author DougLei
 */
public class ProcedureExecutionException extends SessionExecutionException{
	
	public ProcedureExecutionException(String message) {
		super(message);
	}
	public ProcedureExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
