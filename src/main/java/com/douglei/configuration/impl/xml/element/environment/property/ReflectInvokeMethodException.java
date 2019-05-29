package com.douglei.configuration.impl.xml.element.environment.property;

/**
 * 反射调用method异常
 * @author DougLei
 */
class ReflectInvokeMethodException extends RuntimeException{
	private static final long serialVersionUID = -6145010809458973978L;

	public ReflectInvokeMethodException(String message, Throwable cause) {
		super(message, cause);
	}
}
