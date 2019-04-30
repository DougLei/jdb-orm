package com.douglei.sessions.session.persistent;

/**
 * 重复的持久化对象异常
 * @author DougLei
 */
public class RepeatPersistentObjectException extends RuntimeException{
	private static final long serialVersionUID = 7269125661878985825L;

	public RepeatPersistentObjectException() {
		super();
	}

	public RepeatPersistentObjectException(String message) {
		super(message);
	}
}
