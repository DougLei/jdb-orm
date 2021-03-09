package com.douglei.orm.mapping;

/**
 * 映射容器
 * @author DougLei
 */
public interface MappingContainer {
	
	/**
	 * 添加映射属性, 如果存在相同code的映射属性, 将其cover; 如果不存在相同code的映射属性, 将其add
	 * @param property
	 * @return 如果存在相同code的映射属性, 返回被覆盖的映射属性实例, 否则返回null
	 */
	MappingProperty addMappingProperty(MappingProperty property);
	
	/**
	 * 删除映射属性
	 * @param code
	 * @return 返回被删除的映射属性实例, 如果没有映射属性, 返回null
	 */
	MappingProperty deleteMappingProperty(String code);
	
	/**
	 * 获取映射属性
	 * @param code
	 * @return 如果没有查询到, 返回null
	 */
	MappingProperty getMappingProperty(String code);
	
	
	
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
	 * 指定code的映射是否存在
	 * @param code
	 * @return
	 */
	boolean exists(String code);
	
	/**
	 * 清空容器
	 */
	void clear();
}
