package com.douglei.configuration.impl.xml.element.environment.mapping;

/**
 * <mappings>元素重复异常
 * @author DougLei
 */
public class RepeatedMappingsElementException extends RuntimeException{
	private static final long serialVersionUID = 6903243329410442016L;

	public RepeatedMappingsElementException(String message) {
		super(message);
	}
}
