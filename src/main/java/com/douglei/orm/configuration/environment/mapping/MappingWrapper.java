package com.douglei.orm.configuration.environment.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.context.RunMappingConfigurationContext;
import com.douglei.orm.core.metadata.table.TableMetadata;

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
	
	// 是否是表映射
	private boolean isTableMapping(Mapping mapping) {
		return mapping.getMappingType() == MappingType.TABLE;
	}
	
	/**
	 * 初始化存储空间大小
	 * @param size
	 */
	protected void initializeMappingCacheStoreSize(int size) {
		mappingCacheStore.initializeStoreSize(size);
	}
	
	/**
	 * <pre>
	 * 	添加映射
	 * 	如果是表映射, 则顺便create表
	 * </pre>
	 * @return mapping的code
	 */
	protected String addMapping(Mapping mapping){
		mappingCacheStore.addMapping(mapping);
		if(isTableMapping(mapping)) {
			RunMappingConfigurationContext.registerCreateTable((TableMetadata) mapping.getMetadata());
		}
		return mapping.getCode();
	}

	/**
	 * <pre>
	 * 	覆盖映射
	 * 	只对映射操作, 不对实体操作
	 * </pre>
	 * @param mapping
	 * @return mapping的code
	 */
	protected String coverMapping(Mapping mapping) {
		mappingCacheStore.coverMapping(mapping);
		return mapping.getCode();
	}
	
	/**
	 * <pre>
	 * 	删除映射
	 * 	如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCode
	 */
	public void removeMapping(String mappingCode) {
		Mapping mapping = mappingCacheStore.removeMapping(mappingCode);
		if(isTableMapping(mapping)) {
			RunMappingConfigurationContext.registerDropTable((TableMetadata) mapping.getMetadata());
		}
	}
	
	/**
	 * 获取映射
	 * @param mappingCode
	 * @return
	 */
	public Mapping getMapping(String mappingCode) {
		return mappingCacheStore.getMapping(mappingCode);
	}
	
	/**
	 * <pre>
	 * 	动态添加映射
	 * 	如果是表映射, 则顺便create表
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent
	 * @return mapping的code
	 */
	public abstract String dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent);
	/**
	 * <pre>
	 * 	动态覆盖映射
	 * 	只对映射操作, 不对实体操作
	 * </pre>
	 * @param mapping
	 * @return mapping的code
	 */
	public abstract String dynamicCoverMapping(MappingType mappingType, String mappingConfigurationContent);
	/**
	 * <pre>
	 * 	动态删除映射
	 * 	如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCode
	 */
	public abstract void dynamicRemoveMapping(String mappingCode);
	

	@Override
	public void destroy() throws DestroyException {
		logger.debug("{} 开始 destroy", getClass());
		mappingCacheStore.destroy();
		logger.debug("{} 结束 destroy", getClass());
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}