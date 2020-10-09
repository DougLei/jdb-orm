package com.douglei.orm.sessionfactory.sessions.sqlsession;

import com.douglei.orm.sessionfactory.sessions.ExecutionException;

/**
 * 
 * @author DougLei
 */
public class ProcedureExecutionException extends ExecutionException{
	private static final long serialVersionUID = 5244264723410546261L;
	
	public ProcedureExecutionException(String message) {
		super(message);
	}
	public ProcedureExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
