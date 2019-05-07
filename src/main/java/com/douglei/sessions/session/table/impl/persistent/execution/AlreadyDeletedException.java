package com.douglei.sessions.session.table.impl.persistent.execution;

/**
 * 已经被删除异常
 * @author DougLei
 */
public class AlreadyDeletedException extends RuntimeException{
	private static final long serialVersionUID = -2740490565369997008L;

	public AlreadyDeletedException() {
		super();
	}

	public AlreadyDeletedException(String message) {
		super(message);
	}
}
