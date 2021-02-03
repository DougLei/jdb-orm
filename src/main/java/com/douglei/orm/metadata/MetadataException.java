package com.douglei.orm.metadata;

import com.douglei.orm.OrmException;

/**
 * 
 * @author DougLei
 */
public class MetadataException extends OrmException {

	public MetadataException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetadataException(String message) {
		super(message);
	}
}
