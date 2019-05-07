package com.douglei.configuration.impl.xml.element.environment;

/**
 * 反射调用method异常
 * @author DougLei
 */
public class ReflectInvokeMethodException extends RuntimeException{
	private static final long serialVersionUID = -8614195228422335437L;

	public ReflectInvokeMethodException() {
		super();
	}

	public ReflectInvokeMethodException(String message) {
		super(message);
	}

	public ReflectInvokeMethodException(String message, Throwable cause) {
		super(message, cause);
	}
}
