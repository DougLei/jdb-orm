package com.douglei.database.metadata;

/**
 * 元数据验证异常
 * @author DougLei
 */
public class MetadataValidateException extends RuntimeException{
	private static final long serialVersionUID = 5851859376135598907L;

	public MetadataValidateException() {
		super();
	}

	public MetadataValidateException(String message) {
		super(message);
	}

	public MetadataValidateException(String message, Throwable cause) {
		super(message, cause);
	}
}
