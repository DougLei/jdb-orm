package com.douglei.orm.mapping.impl.sql.metadata.parameter;

import java.io.Serializable;

/**
 * 默认值处理器
 * @author DougLei
 */
public class DefaultValueHandler implements Serializable{

	/**
	 * 转换默认值
	 * @param value
	 * @return
	 */
	public Object getDefaultValue(String value) {
		return value;
	}
}
