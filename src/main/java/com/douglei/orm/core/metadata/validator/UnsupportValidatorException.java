package com.douglei.orm.core.metadata.validator;

/**
 * 不支持的验证器异常
 * @author DougLei
 */
public class UnsupportValidatorException extends RuntimeException{
	private static final long serialVersionUID = 1212600292507155463L;

	public UnsupportValidatorException(String validatorName) {
		super("目前不支持名为["+validatorName+"]的验证器");
	}
}
