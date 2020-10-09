package com.douglei.orm.sessionfactory.sessions;

/**
 * session被关闭异常
 * @author DougLei
 */
public class SessionIsClosedException extends SessionExecutionException{
	private static final long serialVersionUID = 5422251103230497699L;

	public SessionIsClosedException() {
		super("session连接已经关闭");
	}
}
