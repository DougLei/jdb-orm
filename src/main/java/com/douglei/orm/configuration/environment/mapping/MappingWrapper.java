package com.douglei.orm.configuration.environment.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.context.RunMappingConfigurationContext;

/**
 * 
 * @author DougLei
 */
public abstract class MappingWrapper implements SelfProcessing{
	private static final Logger logger = LoggerFactory.getLogger(MappingWrapper.class);
	
	protected boolean searchJar;
	private MappingCacheStore mappingCacheStore;
	public MappingWrapper(boolean searchJar, MappingCacheStore mappingCacheStore) {
		this.searchJar = searchJar;
		this.mappingCacheStore = mappingCacheStore;
		logger.debug("searchJar value = {}", searchJar);
	}
	
	/**
	 * 初始化存储空间
	 * @param size
	 */
	protected void initializeMappingCacheStore(int size) {
		mappingCacheStore.initializeStore(size);
	}
	
	/**
	 * <pre>
	 * 	添加映射
	 * 	如果是表映射, 则顺便create表
	 * 	这个方法目前给框架启动时使用, 用来加载已经配置的xml映射
	 * </pre>
	 * @return mapping的code
	 */
	protected String addMapping(Mapping mapping){
		mappingCacheStore.addMapping(mapping);
		RunMappingConfigurationContext.registerCreateTableMapping(mapping);
		return mapping.getCode();
	}

	/**
	 * <pre>
	 * 	添加/覆盖映射
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * 	这个方法用在系统启动后, 动态的添加新的映射或修改映射时使用
	 * </pre>
	 * @param mapping
	 * @return mapping的code
	 */
	protected String addOrCoverMapping(Mapping mapping) {
		mappingCacheStore.addOrCoverMapping(mapping);
		RunMappingConfigurationContext.registerCreateTableMapping(mapping);
		return mapping.getCode();
	}
	
	/**
	 * <pre>
	 * 	添加/覆盖映射
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param mapping
	 * @return mapping的code
	 */
	protected String addOrCoverMapping_simple(Mapping mapping) {
		mappingCacheStore.addOrCoverMapping(mapping);
		return mapping.getCode();
	}
	
	/**
	 * <pre>
	 * 	删除映射
	 * 	如果是表映射, 则顺便drop表
	 * 	这个方法用在系统启动后, 动态的删除映射时使用
	 * </pre>
	 * @param mappingCode
	 */
	protected void removeMapping(String mappingCode) {
		Mapping mapping = mappingCacheStore.removeMapping(mappingCode);
		RunMappingConfigurationContext.registerDropTableMapping(mapping);
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
	 * 	动态添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param mappingConfigurationFilePath 配置文件路径
	 * @return mapping的code
	 */
	public abstract String dynamicAddMapping(String mappingConfigurationFilePath);
	/**
	 * <pre>
	 * 	动态添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent 配置内容
	 * @return mapping的code
	 */
	public abstract String dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent);
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param mappingConfigurationFilePath 配置文件路径
	 * @return mapping的code
	 */
	public abstract String dynamicCoverMapping(String mappingConfigurationFilePath);
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent 配置内容
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
