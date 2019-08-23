package com.douglei.orm.core.result;

/**
 * 结果对象, 可以集成国际化
 * @author DougLei
 */
public abstract class Result {
	public static final String i18nCodePrefix = "jdb.result.";
	
	/**
	 * 返回code, 后续可以集成国际化
	 * @return
	 */
	protected abstract String getI18nCode();
	
	/**
	 * 返回message
	 * @return
	 */
	public abstract String getMessage();
}
