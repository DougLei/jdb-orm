package com.douglei.orm.sessions.session.sql.impl;

/**
 * 非唯一select sql语句异常
 * @author DougLei
 */
public class NonUniqueSelectSqlException extends RuntimeException{
	private static final long serialVersionUID = -8581191653861477133L;

	public NonUniqueSelectSqlException() {
		super();
	}

	public NonUniqueSelectSqlException(String message) {
		super(message);
	}
}
