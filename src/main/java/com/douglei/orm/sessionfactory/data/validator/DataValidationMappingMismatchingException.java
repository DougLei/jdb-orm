package com.douglei.orm.sessionfactory.data.validator;

/**
 * 数据验证时的mapping不匹配异常
 * @author DougLei
 */
public class DataValidationMappingMismatchingException extends RuntimeException{
	private static final long serialVersionUID = -6939622759149518697L;

	public DataValidationMappingMismatchingException(String message) {
		super(message);
	}
}
