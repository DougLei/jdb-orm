package com.douglei.orm.core.dialect.db.table;

/**
 * drop table 异常
 * @author DougLei
 */
public class DropTableException extends RuntimeException{
	private static final long serialVersionUID = -2887773926210519663L;

	public DropTableException(String message, Throwable t) {
		super(message, t);
	}
}
