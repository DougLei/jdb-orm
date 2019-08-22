package com.douglei.orm.core.metadata.validator;

/**
 * 
 * @author DougLei
 */
public abstract class ValidatorResult {
	public static final String i18nCodePrefix = "jdb.validator.";
	
	/**
	 * 返回验证结果message
	 * @return
	 */
	public abstract String getMessage();
	
	/**
	 * 返回code, 后续可以集成国际化
	 * @return
	 */
	protected abstract String getI18nCode();
}
