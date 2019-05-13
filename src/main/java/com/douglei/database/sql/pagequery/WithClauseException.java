package com.douglei.database.sql.pagequery;

/**
 * with子句异常
 * @author DougLei
 */
public class WithClauseException extends RuntimeException{
	private static final long serialVersionUID = -785466672369245161L;

	public WithClauseException() {
		super();
	}

	public WithClauseException(String message) {
		super(message);
	}
}
