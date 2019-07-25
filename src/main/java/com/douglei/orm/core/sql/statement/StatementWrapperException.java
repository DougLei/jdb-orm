package com.douglei.orm.core.sql.statement;

/**
 * 
 * @author DougLei
 */
public class StatementWrapperException extends RuntimeException{
	private static final long serialVersionUID = -5069761886064977815L;
	
	public StatementWrapperException(String message) {
		super(message);
	}
	public StatementWrapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
