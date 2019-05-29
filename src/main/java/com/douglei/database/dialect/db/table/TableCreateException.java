package com.douglei.database.dialect.db.table;

/**
 * 表创建异常
 * @author DougLei
 */
public class TableCreateException extends RuntimeException{
	private static final long serialVersionUID = -5623996625139045599L;

	public TableCreateException(String message) {
		super(message);
	}

	public TableCreateException(String message, Throwable cause) {
		super(message, cause);
	}
}
