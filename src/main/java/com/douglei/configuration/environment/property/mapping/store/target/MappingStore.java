package com.douglei.configuration.environment.property.mapping.store.target;

import com.douglei.configuration.SelfProcessing;
import com.douglei.configuration.environment.mapping.Mapping;

/**
 * 映射信息存储的目标位置
 * @author DougLei
 */
public interface MappingStore extends SelfProcessing{
	
	/**
	 * 初始化存储空间大小
	 * @param size 如果size<1, 则使用默认的大小
	 */
	void initialStoreSize(int size);
	
	/**
	 * 添加映射
	 * <pre>
	 * 	如果已经存在相同code的mapping，则抛出异常
	 * </pre>
	 * @param mapping
	 */
	void addMapping(Mapping mapping);
	
	/**
	 * 覆盖映射
	 * @param mapping
	 */
	void coverMapping(Mapping mapping);

	/**
	 * 动态移除映射
	 * @param mappingCode
	 */
	void dynamicRemoveMapping(String mappingCode);
	
	/**
	 * 获取映射
	 * @param mappingCode
	 * @return
	 */
	Mapping getMapping(String mappingCode);
}
