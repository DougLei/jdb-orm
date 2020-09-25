package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node;

import com.douglei.orm.core.metadata.MetadataResolvingException;

/**
 * sql node解析异常
 * @author DougLei
 */
public class SqlNodeResolvingException extends MetadataResolvingException{
	private static final long serialVersionUID = -6390370032453654570L;

	public SqlNodeResolvingException(String message) {
		super(message);
	}
}
