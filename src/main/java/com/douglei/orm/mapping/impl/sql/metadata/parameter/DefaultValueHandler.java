package com.douglei.orm.mapping.impl.sql.metadata.parameter;

import java.io.Serializable;

/**
 * 默认值处理器
 * @author DougLei
 */
public class DefaultValueHandler implements Serializable{
	private static final long serialVersionUID = -6977665783075620833L;

	/**
	 * 转换默认值
	 * @param configDefaultValue 给参数配置的默认值
	 * @return
	 */
	public Object getDefaultValue(String configDefaultValue) {
		return configDefaultValue;
	}
}
