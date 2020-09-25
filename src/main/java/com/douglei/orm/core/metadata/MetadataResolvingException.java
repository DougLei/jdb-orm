package com.douglei.orm.core.metadata;

/**
 * 元数据解析异常
 * @author DougLei
 */
public class MetadataResolvingException extends RuntimeException{
	private static final long serialVersionUID = -2424792234267248046L;
	
	public MetadataResolvingException(String message) {
		super(message);
	}
	public MetadataResolvingException(String message, Throwable cause) {
		super(message, cause);
	}
}
