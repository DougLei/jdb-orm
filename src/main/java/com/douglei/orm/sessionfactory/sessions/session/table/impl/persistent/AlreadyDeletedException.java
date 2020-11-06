package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

/**
 * 已经被删除异常
 * @author DougLei
 */
public class AlreadyDeletedException extends RuntimeException{
	private static final long serialVersionUID = 2758092953142442454L;

	public AlreadyDeletedException(String message) {
		super(message);
	}
}
