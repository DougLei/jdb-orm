package com.douglei.orm.sessions.sqlsession;

import com.douglei.orm.sessions.ExecutionException;

/**
 * 
 * @author DougLei
 */
public class ProcedureExecutionException extends ExecutionException{
	private static final long serialVersionUID = -4899108837447055907L;
	
	public ProcedureExecutionException(String message) {
		super(message);
	}
	public ProcedureExecutionException(String message, Throwable t) {
		super(message, t);
	}
}
