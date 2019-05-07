package com.douglei.configuration.impl.xml.element.environment.mapping;

/**
 * <mappings>元素重复异常
 * @author DougLei
 */
public class RepeatMappingsElementException extends RuntimeException{
	private static final long serialVersionUID = -606911267992663208L;

	public RepeatMappingsElementException() {
		super();
	}

	public RepeatMappingsElementException(String message) {
		super(message);
	}
}
