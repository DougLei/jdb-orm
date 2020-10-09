package com.douglei.orm.sessionfactory.sessions.session.table.impl;

/**
 * 已经被删除异常
 * @author DougLei
 */
public class AlreadyDeletedException extends RuntimeException{
	private static final long serialVersionUID = 8956512466222266421L;

	public AlreadyDeletedException(String message) {
		super(message);
	}
}
