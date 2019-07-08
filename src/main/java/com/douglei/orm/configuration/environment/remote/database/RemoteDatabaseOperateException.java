package com.douglei.orm.configuration.environment.remote.database;

/**
 * 远程数据库操作异常
 * @author DougLei
 */
public class RemoteDatabaseOperateException extends RuntimeException{
	private static final long serialVersionUID = -3320523402442239839L;

	public RemoteDatabaseOperateException(String message, Throwable cause) {
		super(message, cause);
	}
}
