package com.douglei.orm.configuration.environment.datasource;

/**
 * 
 * @author DougLei
 */
public class ConnectionWrapperException extends RuntimeException{
	private static final long serialVersionUID = -8286813307549238988L;
	
	public ConnectionWrapperException(String message) {
		super(message);
	}
	public ConnectionWrapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
