package com.douglei.orm;

/**
 * 销毁异常
 * @author DougLei
 */
public class DestroyException extends RuntimeException{

	public DestroyException(String message, Throwable cause) {
		super(message, cause);
	}
}
