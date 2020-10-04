package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node;

import com.douglei.orm.mapping.metadata.parser.MetadataParseException;

/**
 * sql node解析异常
 * @author DougLei
 */
public class SqlNodeParserException extends MetadataParseException{

	public SqlNodeParserException(String message) {
		super(message);
	}
}
