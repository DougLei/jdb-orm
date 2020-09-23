package com.douglei.orm.core.metadata;

/**
 * 元数据解析异常
 * @author DougLei
 */
public class MetadataResolvingException extends RuntimeException{
	
	public MetadataResolvingException(String message) {
		super(message);
	}
	public MetadataResolvingException(String message, Throwable cause) {
		super(message, cause);
	}
}
