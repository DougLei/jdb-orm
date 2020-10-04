package com.douglei.orm.sessionfactory.sessions.session.table.impl;

/**
 * 已经被删除异常
 * @author DougLei
 */
public class AlreadyDeletedException extends RuntimeException{

	public AlreadyDeletedException(String message) {
		super(message);
	}
}
