package com.douglei.orm.sessions;

/**
 * session被关闭异常
 * @author DougLei
 */
public class SessionIsClosedException extends RuntimeException{
	private static final long serialVersionUID = -1623545764050849786L;

	public SessionIsClosedException() {
		super("session连接已经关闭");
	}
}
