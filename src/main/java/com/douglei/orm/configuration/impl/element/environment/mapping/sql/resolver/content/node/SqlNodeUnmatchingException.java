package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node;

import com.douglei.orm.core.metadata.MetadataValidateException;

/**
 * sql node不匹配异常
 * @author DougLei
 */
public class SqlNodeUnmatchingException extends MetadataValidateException{
	private static final long serialVersionUID = 8214630363699813836L;

	public SqlNodeUnmatchingException(String message) {
		super(message);
	}
}
