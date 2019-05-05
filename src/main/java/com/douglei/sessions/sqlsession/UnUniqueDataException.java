package com.douglei.sessions.sqlsession;

/**
 * 非唯一数据异常
 * @author DougLei
 */
public class UnUniqueDataException extends RuntimeException{
	private static final long serialVersionUID = -8220620331370335976L;

	public UnUniqueDataException() {
		super();
	}

	public UnUniqueDataException(String message) {
		super(message);
	}
}
