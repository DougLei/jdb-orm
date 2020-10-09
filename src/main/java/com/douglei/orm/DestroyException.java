package com.douglei.orm;

/**
 * 销毁异常
 * @author DougLei
 */
public class DestroyException extends RuntimeException{
	private static final long serialVersionUID = -26330067800715494L;

	public DestroyException(String message, Throwable cause) {
		super(message, cause);
	}
}
