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
	public abstract String getI18nCode();
	
	/**
	 * 匹配国际化 {@link Result#getI18nCode()} 对应的message中, 声明的占位符的值数组
	 * @return
	 */
	public Object[] getI18nParams() {
		return null;
	}
	
	/**
	 * 返回message
	 * @return
	 */
	public abstract String getMessage();
}
