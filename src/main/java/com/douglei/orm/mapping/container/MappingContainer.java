package com.douglei.orm.mapping.container;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingFeature;

/**
 * 映射特性和映射一定是成对出现的
 * @author DougLei
 */
public interface MappingContainer {
	
	/**
	 * 清空存储容器
	 */
	void clear();
	
	/**
	 * 添加映射特性, 如果存在相同code的映射特性, 将其cover; 如果不存在相同code的映射特性, 将其add
	 * @param mappingFeature
	 * @return 如果存在相同code的映射特性, 返回被覆盖的映射特性实例, 否则返回null
	 */
	MappingFeature addMappingFeature(MappingFeature mappingFeature);
	
	/**
	 * 删除映射特性
	 * @param code
	 * @return 返回被删除的映射特性实例, 如果没有映射特性, 返回null
	 */
	MappingFeature deleteMappingFeature(String code);
	
	/**
	 * 获取映射特性
	 * @param code
	 * @return 如果没有查询到, 返回null
	 */
	MappingFeature getMappingFeature(String code);
	
	/**
	 * 添加映射, 如果存在相同code的映射, 将其cover; 如果不存在相同code的映射, 将其add
	 * @param mapping
	 * @return 如果存在相同code的映射, 返回被覆盖的映射实例, 否则返回null
	 */
	Mapping addMapping(Mapping mapping);
	
	/**
	 * 删除映射
	 * @param code
	 * @return 返回被删除的映射实例, 如果没有映射, 返回null
	 */
	Mapping deleteMapping(String code);
	
	/**
	 * 获取映射
	 * @param code
	 * @return 如果没有查询到, 返回null
	 */
	Mapping getMapping(String code);
	
	/**
	 * 指定code的映射(特性)是否存在
	 * @param code
	 * @return
	 */
	boolean exists(String code);
}
