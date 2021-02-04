package com.douglei.orm.dialect.datatype;

import com.douglei.orm.configuration.OrmException;

/**
 * 
 * @author DougLei
 */
public class DataTypeException extends OrmException{

	public DataTypeException(String message) {
		super(message);
	}
	public DataTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
