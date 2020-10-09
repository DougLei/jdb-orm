package com.douglei.orm.mapping.metadata.parser;

/**
 * 元数据解析异常
 * @author DougLei
 */
public class MetadataParseException extends RuntimeException{
	private static final long serialVersionUID = 5941342349787872166L;

	public MetadataParseException(String message) {
		super(message);
	}
}
