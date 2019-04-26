package com.douglei.configuration.environment.mapping;

/**
 * 
 * @author DougLei
 */
public interface Mapping {
	
	/**
	 * 获取映射编码，绝对唯一
	 * <pre>
	 * 	表类型的映射: 如果指定了className, 则返回className; 否则返回name, 即表名
	 * </pre>
	 * @return
	 */
	String getCode();
	
	/**
	 * 获取映射的类型
	 * @return
	 */
	MappingType getMappingType();
}
