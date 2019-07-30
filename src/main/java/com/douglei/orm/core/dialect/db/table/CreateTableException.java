package com.douglei.orm.core.dialect.db.table;

/**
 * create table 异常
 * @author DougLei
 */
public class CreateTableException extends RuntimeException{
	private static final long serialVersionUID = -658212218576528917L;

	public CreateTableException(String message, Throwable t) {
		super(message, t);
	}
}
