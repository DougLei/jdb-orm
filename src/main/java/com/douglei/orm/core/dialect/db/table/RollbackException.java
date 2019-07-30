package com.douglei.orm.core.dialect.db.table;

/**
 * 回滚异常
 * @author DougLei
 */
public class RollbackException extends RuntimeException{
	private static final long serialVersionUID = -7477392917441784624L;

	public RollbackException(String message, Throwable t) {
		super(message, t);
	}
}
