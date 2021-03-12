package com.douglei.orm.mapping.impl.sql.executor.content.node;

import com.douglei.orm.sessionfactory.sessions.SessionExecuteException;

/**
 * 
 * @author DougLei
 */
public class SqlNodeExecuteException extends SessionExecuteException{

	public SqlNodeExecuteException(String message) {
		super(message);
	}
}
