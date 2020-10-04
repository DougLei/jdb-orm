package com.douglei.orm.sql;

/**
 * with子句异常
 * @author DougLei
 */
public class WithClauseException extends RuntimeException{
	public WithClauseException(String message) {
		super(message);
	}
}