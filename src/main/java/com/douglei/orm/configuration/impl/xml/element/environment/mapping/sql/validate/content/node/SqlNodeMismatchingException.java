package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import com.douglei.orm.core.metadata.MetadataValidateException;

/**
 * sql node不匹配异常
 * @author DougLei
 */
public class SqlNodeMismatchingException extends MetadataValidateException{
	private static final long serialVersionUID = -1261093446312232208L;

	public SqlNodeMismatchingException(String message) {
		super(message);
	}
}
