package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

/**
 * 重复的持久化对象异常
 * @author DougLei
 */
public class RepeatedPersistentObjectException extends RuntimeException{

	public RepeatedPersistentObjectException(String message) {
		super(message);
	}
}
