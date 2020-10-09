package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node;

import com.douglei.orm.mapping.metadata.parser.MetadataParseException;

/**
 * sql node解析异常
 * @author DougLei
 */
public class SqlNodeParserException extends MetadataParseException{
	private static final long serialVersionUID = -1934703178774042215L;

	public SqlNodeParserException(String message) {
		super(message);
	}
}
