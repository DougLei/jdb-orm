package com.douglei.sessions.session.persistent.id;

/**
 * Identity不支持的数据类型异常
 * @author DougLei
 */
public class UnsupportedIdentityDataTypeException extends RuntimeException{
	private static final long serialVersionUID = -2394565482453349475L;

	public UnsupportedIdentityDataTypeException() {
		super();
	}

	public UnsupportedIdentityDataTypeException(String message) {
		super(message);
	}
}
