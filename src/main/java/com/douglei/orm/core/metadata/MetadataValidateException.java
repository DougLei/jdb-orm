package com.douglei.orm.core.metadata;

/**
 * 元数据验证异常
 * @author DougLei
 */
public class MetadataValidateException extends RuntimeException{
	private static final long serialVersionUID = 1474847356021052285L;

	public MetadataValidateException(String message) {
		super(message);
	}

	public MetadataValidateException(String message, Throwable cause) {
		super(message, cause);
	}
}
