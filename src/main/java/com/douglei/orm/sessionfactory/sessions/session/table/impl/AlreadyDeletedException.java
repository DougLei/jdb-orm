package com.douglei.orm.sessionfactory.sessions.session.table.impl;

/**
 * 已经被删除异常
 * @author DougLei
 */
public class AlreadyDeletedException extends RuntimeException{
	private static final long serialVersionUID = 7286542664388696477L;

	public AlreadyDeletedException() {
		super();
	}

	public AlreadyDeletedException(String message) {
		super(message);
	}
}
