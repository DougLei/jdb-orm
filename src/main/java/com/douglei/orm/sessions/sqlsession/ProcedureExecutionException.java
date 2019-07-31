package com.douglei.orm.sessions.sqlsession;

import com.douglei.orm.sessions.SessionExecutionException;

/**
 * 
 * @author DougLei
 */
public class ProcedureExecutionException extends SessionExecutionException{
	private static final long serialVersionUID = -4899108837447055907L;
	
	public ProcedureExecutionException(String message) {
		super(message);
	}
	public ProcedureExecutionException(String message, Throwable t) {
		super(message, t);
	}
}