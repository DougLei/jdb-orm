package com.douglei.orm.mapping.impl.sql.executor.content.node;

import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;

/**
 * 
 * @author DougLei
 */
public class SqlNodeExecuteException extends SessionExecutionException{

	public SqlNodeExecuteException(String message) {
		super(message);
	}
}
