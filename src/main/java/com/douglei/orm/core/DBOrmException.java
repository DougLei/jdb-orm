package com.douglei.orm.core;

import com.douglei.tools.RootRuntimeException;

/**
 * 
 * @author DougLei
 */
public class DBOrmException extends RootRuntimeException {
	private static final long serialVersionUID = 3738560687002962748L;

	public DBOrmException() {
		super();
	}

	public DBOrmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBOrmException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBOrmException(String message) {
		super(message);
	}

	public DBOrmException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getName() {
		return "jdb-orm";
	}
}
