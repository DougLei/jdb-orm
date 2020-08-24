package com.douglei.orm.core.metadata.sql;

/**
 * 默认值处理器
 * @author DougLei
 */
public class DefaultValueHandler {
	
	/**
	 * 转换默认值
	 * @param value
	 * @return
	 */
	public Object getDefaultValue(String value) {
		return value;
	}
}
