package com.douglei.orm.sql;

/**
 * with子句异常
 * @author DougLei
 */
public class WithClauseException extends RuntimeException{
	private static final long serialVersionUID = 7127452588162474420L;

	public WithClauseException(String message) {
		super(message);
	}
}