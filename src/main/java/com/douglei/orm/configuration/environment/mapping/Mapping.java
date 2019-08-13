package com.douglei.orm.configuration.environment.mapping;

import java.io.Serializable;

import com.douglei.orm.core.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public interface Mapping extends Serializable{
	
	/**
	 * 获取映射编码，绝对唯一
	 * <pre>
	 * 	表类型的映射: 如果指定了className, 则返回className; 否则返回name, 即表名
	 * 	sql类型的映射: 如果指定了namespace, 则返回namespace.name, 否则只返回name
	 * </pre>
	 * @return
	 */
	String getCode();
	
	/**
	 * 获取映射的类型
	 * @return
	 */
	MappingType getMappingType();

	/**
	 * 获取元数据信息
	 * @return
	 */
	Metadata getMetadata();
}
