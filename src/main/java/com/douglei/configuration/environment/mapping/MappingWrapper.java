package com.douglei.configuration.environment.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.DestroyException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.SelfProcessing;
import com.douglei.configuration.environment.property.mapping.cache.store.MappingCacheStore;

/**
 * 
 * @author DougLei
 */
public abstract class MappingWrapper implements SelfProcessing{
	private static final Logger logger = LoggerFactory.getLogger(MappingWrapper.class);
	
	private MappingCacheStore mappingCacheStore;
	public MappingWrapper(MappingCacheStore mappingCacheStore) {
		this.mappingCacheStore = mappingCacheStore;
	}
	
	/**
	 * 初始化存储空间大小
	 * @param size
	 */
	protected void initialMappingCacheStoreSize(int size) {
		mappingCacheStore.initialStoreSize(size);
	}
	
	/**
	 * 添加映射
	 * <pre>
	 * 	如果已经存在相同code的mapping，则抛出异常
	 * </pre>
	 * @param mapping
	 */
	protected void addMapping(Mapping mapping){
		mappingCacheStore.addMapping(mapping);
	}
	
	/**
	 * 动态添加映射, 如果存在, 则覆盖
	 * @param mappingType
	 * @param mappingConfigurationContent
	 */
	public abstract void dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent);
	
	/**
	 * 覆盖映射
	 * @param mapping
	 */
	protected void coverMapping(Mapping mapping) {
		mappingCacheStore.coverMapping(mapping);
	}
	
	/**
	 * 动态移除映射
	 * @param mappingCode
	 */
	public void dynamicRemoveMapping(String mappingCode) {
		mappingCacheStore.dynamicRemoveMapping(mappingCode);
	}
	
	/**
	 * 获取映射
	 * @param mappingCode
	 * @return
	 */
	public Mapping getMapping(String mappingCode) {
		return mappingCacheStore.getMapping(mappingCode);
	}

	@Override
	public void doDestroy() throws DestroyException {
		logger.debug("{} 开始 destroy", getClass());
		mappingCacheStore.doDestroy();
		logger.debug("{} 结束 destroy", getClass());
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
