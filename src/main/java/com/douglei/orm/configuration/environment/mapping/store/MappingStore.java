package com.douglei.orm.configuration.environment.mapping.store;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.Mapping;

/**
 * 
 * @author DougLei
 */
public interface MappingStore extends SelfProcessing{
	
	/**
	 * 清空存储容器
	 */
	default void clear() {
	}
	
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
}
