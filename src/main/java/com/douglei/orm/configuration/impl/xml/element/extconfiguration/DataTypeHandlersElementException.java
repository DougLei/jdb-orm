package com.douglei.orm.configuration.impl.xml.element.extconfiguration;

/**
 * <datatype-handlers>元素重复异常
 * @author DougLei
 */
public class DataTypeHandlersElementException extends RuntimeException{
	private static final long serialVersionUID = 1592791931499003513L;

	public DataTypeHandlersElementException() {
		super();
	}

	public DataTypeHandlersElementException(String message) {
		super(message);
	}
}
