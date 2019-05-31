package com.douglei.configuration.environment.mapping.cache.store;

import com.douglei.configuration.SelfProcessing;
import com.douglei.configuration.environment.mapping.Mapping;

/**
 * 映射信息缓存存储的目标位置
 * @author DougLei
 */
public interface MappingCacheStore extends SelfProcessing{
	static final short DEFAULT_STORE_SIZE = 32;
	
	/**
	 * 初始化存储空间大小
	 * @param size 如果size<1, 则使用默认的大小
	 */
	void initialStoreSize(int size);
	
	/**
	 * 添加映射
	 * @param mapping
	 * @throws RepeatedMappingException
	 */
	void addMapping(Mapping mapping) throws RepeatedMappingException;
	
	/**
	 * 覆盖映射
	 * @param mapping
	 */
	void coverMapping(Mapping mapping);

	/**
	 * 移除映射
	 * @param mappingCode
	 * @return
	 * @throws NotExistsMappingException
	 */
	Mapping removeMapping(String mappingCode) throws NotExistsMappingException;
	
	/**
	 * 获取映射
	 * @param mappingCode
	 * @return
	 * @throws NotExistsMappingException
	 */
	Mapping getMapping(String mappingCode) throws NotExistsMappingException;
	
	/**
	 * mapping是否存在
	 * @param mappingCode
	 * @return
	 */
	boolean mappingExists(String mappingCode);
}
