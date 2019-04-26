package com.douglei.database.metadata;

/**
 * 元数据验证异常
 * @author DougLei
 */
public class MetadataValidateException extends RuntimeException{
	private static final long serialVersionUID = 3979326127557070569L;

	public MetadataValidateException() {
		super();
	}

	public MetadataValidateException(String message) {
		super(message);
	}
}
