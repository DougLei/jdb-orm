package com.douglei.orm.configuration.environment.mapping.store;

import java.util.Collection;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.Mapping;

/**
 * 
 * @author DougLei
 */
public interface MappingStore extends SelfProcessing{
	
	/**
	 * 初始化存储容器
	 */
	default void initializeStore() {
	}
	/**
	 * 清空存储容器
	 */
	default void clearStore() {
	}
	
	/** 
	 * 添加映射, 如果出现相同的code值, 必须抛出 {@link RepeatedMappingException} 
	 * 该方法目前只在框架启动时使用
	 * @param mapping
	 * @throws RepeatedMappingException
	 */
	void addMapping(Mapping mapping) throws RepeatedMappingException;
	/**
	 * 批量addMapping, 如果出现相同的code值, 必须抛出 {@link RepeatedMappingException} 
	 * 该方法是在回滚的时候使用到
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
	 * @param code
	 * @return 返回映射, 如果是表映射, 还可能需要将该表删除
	 * @throws NotExistsMappingException
	 */
	Mapping removeMapping(String code) throws NotExistsMappingException;
	/**
	 * 批量removeMapping
	 * 该方法是在回滚的时候使用到
	 * @param codes
	 * @throws NotExistsMappingException
	 */
	void removeMapping(Collection<String> codes) throws NotExistsMappingException;
	
	/**
	 * 获取映射
	 * @param code
	 * @return
	 * @throws NotExistsMappingException
	 */
	Mapping getMapping(String code) throws NotExistsMappingException;
	
	/**
	 * 指定code的mapping是否存在
	 * @param code
	 * @return
	 */
	boolean mappingExists(String code);
}
