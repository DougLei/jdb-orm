package com.douglei.orm.configuration.environment.mapping.cache.store;

import java.util.Collection;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.Mapping;

/**
 * 映射信息缓存存储的目标位置
 * @author DougLei
 */
public interface MappingCacheStore extends SelfProcessing{
	static final short DEFAULT_STORE_SIZE = 64;
	
	/**
	 * 初始化存储空间对象
	 * <b>如果需要清空之前的数据, 也在这个方法中实现</b>
	 * @param size 如果size<1, 则使用默认的大小
	 */
	default void initializeStore(int size) {
	}
	
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
	 * 批量addMapping
	 * @param mappings
	 * @throws RepeatedMappingException
	 */
	void addMapping(Collection<Mapping> mappings) throws RepeatedMappingException;
	
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
	 * 批量addOrCoverMapping
	 * @param mappings
	 */
	void addOrCoverMapping(Collection<Mapping> mappings);
	
	/**
	 * 移除映射
	 * @param mappingCode
	 * @return
	 * @throws NotExistsMappingException
	 */
	Mapping removeMapping(String mappingCode) throws NotExistsMappingException;
	/**
	 * 批量removeMapping
	 * @param mappingCodes
	 * @throws NotExistsMappingException
	 */
	void removeMapping(Collection<String> mappingCodes) throws NotExistsMappingException;
	
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
