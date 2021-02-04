package com.douglei.orm.configuration.environment.datasource;

import com.douglei.orm.configuration.OrmException;

/**
 * 
 * @author DougLei
 */
public class ConnectionException extends OrmException{
	
	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
