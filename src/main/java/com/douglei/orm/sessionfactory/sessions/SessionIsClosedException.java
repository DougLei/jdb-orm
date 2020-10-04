package com.douglei.orm.sessionfactory.sessions;

/**
 * session被关闭异常
 * @author DougLei
 */
public class SessionIsClosedException extends SessionExecutionException{

	public SessionIsClosedException() {
		super("session连接已经关闭");
	}
}
