package com.douglei.orm.configuration.environment.mapping.store;

import java.util.Collection;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.impl.redis.RedisMappingStore;

/**
 * 
 * @author DougLei
 */
public interface MappingStore extends SelfProcessing{
	static final short DEFAULT_STORE_SIZE = 32;
	
	/**
	 * <pre>
	 * 初始化存储空间对象
	 * 如果需要清空数据, 必须根据 {@link EnvironmentContext#getEnvironmentProperty()#clearMappingOnStart()} 的值判断, 是否需要清空之前的映射数据
	 * 具体可以参考 {@link RedisMappingStore#addMapping(Mapping)}
	 * </pre>
	 * @param size 如果size<1, 则使用默认的大小
	 */
	void initializeStore(int size);
	
	/**
	 * <pre>
	 * 	添加映射
	 * 	如果存在相同code的映射, <b>判断 {@link EnvironmentContext#getEnvironmentProperty()#clearMappingOnStart()} 值, 如果为true, 则抛出异常, 如果为false, 则跳过该映射的add</b>
	 * 具体可以参考 {@link RedisMappingStore#addMapping(Mapping)}
	 * </pre>
	 * @param mapping
	 * @throws RepeatedMappingException
	 */
	void addMapping(Mapping mapping) throws RepeatedMappingException;
	/**
	 * 批量addMapping, 该方法是在回滚的时候使用到
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
	 * 移除映射
	 * @param mappingCode
	 * @return 返回映射, 如果是表映射, 还可能需要将该表删除
	 * @throws NotExistsMappingException
	 */
	Mapping removeMapping(String mappingCode) throws NotExistsMappingException;
	/**
	 * 批量removeMapping, 该方法是在回滚的时候使用到
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
	 * 指定code的mapping是否存在
	 * @param mappingCode
	 * @return
	 */
	boolean mappingExists(String mappingCode);
}
