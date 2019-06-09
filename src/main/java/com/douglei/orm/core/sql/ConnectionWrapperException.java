package com.douglei.orm.core.sql;

/**
 * 
 * @author DougLei
 */
public class ConnectionWrapperException extends RuntimeException{
	private static final long serialVersionUID = -2813341347102343768L;
	
	public ConnectionWrapperException(String message) {
		super(message);
	}
	public ConnectionWrapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
