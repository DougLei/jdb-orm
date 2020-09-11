package com.douglei.orm.core.sql;

/**
 * with子句异常
 * @author DougLei
 */
public class WithClauseException extends RuntimeException{
	private static final long serialVersionUID = -5897286743229926577L;
	public WithClauseException(String message) {
		super(message);
	}
}