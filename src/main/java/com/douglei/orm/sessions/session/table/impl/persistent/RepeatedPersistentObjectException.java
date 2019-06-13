package com.douglei.orm.sessions.session.table.impl.persistent;

/**
 * 重复的持久化对象异常
 * @author DougLei
 */
public class RepeatedPersistentObjectException extends RuntimeException{
	private static final long serialVersionUID = 8587123639380424354L;

	public RepeatedPersistentObjectException(String message) {
		super(message);
	}
}
