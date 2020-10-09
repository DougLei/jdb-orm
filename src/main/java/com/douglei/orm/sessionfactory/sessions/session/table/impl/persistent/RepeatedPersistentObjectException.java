package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

/**
 * 重复的持久化对象异常
 * @author DougLei
 */
public class RepeatedPersistentObjectException extends RuntimeException{
	private static final long serialVersionUID = -3362180729830323908L;

	public RepeatedPersistentObjectException(String message) {
		super(message);
	}
}
