package com.douglei.orm.environment.datasource;

/**
 * 
 * @author DougLei
 */
public class ConnectionWrapperException extends RuntimeException{
	
	public ConnectionWrapperException(String message) {
		super(message);
	}
	public ConnectionWrapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
