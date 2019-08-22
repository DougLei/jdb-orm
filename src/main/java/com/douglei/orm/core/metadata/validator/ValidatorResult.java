package com.douglei.orm.core.metadata.validator;

/**
 * 
 * @author DougLei
 */
public abstract class ValidatorResult {
	private static final String i18nCodePrefix = "jdb.validator.";
	
	/**
	 * 返回验证结果message
	 * @return
	 */
	public abstract String getMessage();
	
	/**
	 * 返回code, 后续可以集成国际化
	 * 子类只要返回code的后缀, 前缀由父类自动追加
	 * @return
	 */
	protected abstract String getI18nCode_();
	
	/**
	 * 返回code, 后续可以集成国际化
	 * @return
	 */
	public final String getI18nCode() {
		return i18nCodePrefix + getI18nCode_();
	}
}
