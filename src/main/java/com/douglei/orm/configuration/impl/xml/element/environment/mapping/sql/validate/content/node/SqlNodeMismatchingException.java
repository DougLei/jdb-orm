package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

/**
 * sql node不匹配异常
 * @author DougLei
 */
public class SqlNodeMismatchingException extends RuntimeException{
	private static final long serialVersionUID = -8779540792288568743L;

	public SqlNodeMismatchingException(String message) {
		super(message);
	}
}
