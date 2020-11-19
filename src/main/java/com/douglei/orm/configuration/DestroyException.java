package com.douglei.orm.configuration;

/**
 * 销毁异常
 * @author DougLei
 */
public class DestroyException extends JDBORMException{
	private static final long serialVersionUID = 4374695195774132573L;

	public DestroyException(String message, Throwable cause) {
		super(message, cause);
	}
}
