package com.douglei.orm.factory.sessions;

/**
 * session被关闭异常
 * @author DougLei
 */
public class SessionIsClosedException extends SessionExecutionException{
	private static final long serialVersionUID = 7280680097808716913L;

	public SessionIsClosedException() {
		super("session连接已经关闭");
	}
}
