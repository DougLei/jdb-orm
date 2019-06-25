package com.douglei.orm.configuration.environment.mapping.cache.store;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.Mapping;

/**
 * 映射信息缓存存储的目标位置
 * @author DougLei
 */
public interface MappingCacheStore extends SelfProcessing{
	static final short DEFAULT_STORE_SIZE = 64;
	
	/**
	 * 初始化存储空间大小
	 * @param size 如果size<1, 则使用默认的大小
	 */
	void initializeStoreSize(int size);
	
	/**
	 * <pre>
	 * 	添加映射
	 * 	如果存在相同code的映射, 会抛出异常
	 * </pre>
	 * @param mapping
	 * @throws RepeatedMappingException
	 */
	void addMapping(Mapping mapping) throws RepeatedMappingException;
	
	/**
	 * <pre>
	 * 	添加/覆盖映射
	 * 	如果存在相同code的映射, 将其cover
	 * 	如果不存在相同code的映射, 将其add
	 * </pre>
	 * @param mapping
	 */
	void addOrCoverMapping(Mapping mapping);
	
	/**
	 * <pre>
	 * 	覆盖映射, 如果不存在则抛出异常
	 * 	如果存在相同code的映射, 将其cover
	 * </pre>
	 * @param mapping
	 * @throws NotExistsMappingException
	 */
	void coverMapping(Mapping mapping) throws NotExistsMappingException;
	
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
