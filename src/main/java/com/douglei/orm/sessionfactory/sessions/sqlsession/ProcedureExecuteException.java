package com.douglei.orm.sessionfactory.sessions.sqlsession;

import com.douglei.orm.sessionfactory.sessions.SessionExecuteException;

/**
 * 
 * @author DougLei
 */
public class ProcedureExecuteException extends SessionExecuteException{
	
	public ProcedureExecuteException(String message) {
		super(message);
	}
	public ProcedureExecuteException(String message, Throwable t) {
		super(message, t);
	}
}
