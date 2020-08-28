package com.douglei.orm.configuration.impl.element.environment.mapping.sql.validator.content.node;

import com.douglei.orm.core.metadata.MetadataValidateException;

/**
 * sql node不匹配异常
 * @author DougLei
 */
public class SqlNodeUnmatchingException extends MetadataValidateException{
	private static final long serialVersionUID = -379651492563670668L;

	public SqlNodeUnmatchingException(String message) {
		super(message);
	}
}
