package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import com.douglei.orm.core.metadata.MetadataValidateException;

/**
 * sql node不匹配异常
 * @author DougLei
 */
public class SqlNodeUnmatchingException extends MetadataValidateException{
	private static final long serialVersionUID = 5747962360494994816L;

	public SqlNodeUnmatchingException(String message) {
		super(message);
	}
}
