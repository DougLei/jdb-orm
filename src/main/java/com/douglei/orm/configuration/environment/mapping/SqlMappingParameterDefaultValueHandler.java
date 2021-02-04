package com.douglei.orm.configuration.environment.mapping;

/**
 * sql映射的参数默认值处理器
 * @author DougLei
 */
public class SqlMappingParameterDefaultValueHandler {

	/**
	 * 获取默认值
	 * @param configDefaultValue sql映射中给参数配置的默认值
	 * @return
	 */
	public Object getDefaultValue(String configDefaultValue) {
		return configDefaultValue;
	}
}
