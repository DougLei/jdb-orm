package com.douglei.orm.configuration.environment.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.XmlMappingFactory;
import com.douglei.orm.context.xml.MappingXmlConfigContext;

/**
 * 
 * @author DougLei
 */
public abstract class MappingWrapper implements SelfProcessing{
	private static final Logger logger = LoggerFactory.getLogger(MappingWrapper.class);
	
	protected boolean searchAll;
	private MappingStore mappingStore;
	public MappingWrapper(boolean searchAll, MappingStore mappingStore) {
		this.searchAll = searchAll;
		this.mappingStore = mappingStore;
		logger.debug("searchAll value = {}", searchAll);
	}
	
	// --------------------------------------------------------------------------------------------------------------
	/**
	 * 初始化存储空间
	 * @param clearMappingStoreOnStart
	 * @return 
	 */
	protected boolean initialMappingStore(boolean clearMappingStoreOnStart) {
		if(clearMappingStoreOnStart) {
			mappingStore.clearStore();
		}
		mappingStore.initializeStore();
		return clearMappingStoreOnStart;
	}
	
	/**
	 * <pre>
	 * 	添加映射
	 * 	如果是表映射, 则顺便create表
	 * 	这个方法只在框架启动时使用, 用来加载resource中xml映射文件
	 * </pre>
	 * @return mapping的code
	 */
	protected String addMapping(Mapping mapping){
		mappingStore.addMapping(mapping);
		MappingXmlConfigContext.addCreateTableMapping(mapping);
		return mapping.getCode();
	}

	
	// --------------------------------------------------------------------------------------------------------------
	/**
	 * 添加或覆盖映射
	 * @param mapping
	 * @param operTableEntity 是否操作表实体, 如果传入true, 则会去操作数据库中的表结构
	 * @return
	 */
	private String addOrCoverMapping(Mapping mapping, boolean operTableEntity) {
		mappingStore.addOrCoverMapping(mapping);
		if(operTableEntity)
			MappingXmlConfigContext.addCreateTableMapping(mapping);
		return mapping.getCode();
	}
	
	/**
	 * 动态添加映射, 如果存在则覆盖
	 * 如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * @param mappingConfigurationFilePath
	 * @return
	 * @throws DynamicAddMappingException
	 */
	public String dynamicAddMapping(String mappingConfigurationFilePath) throws DynamicAddMappingException {
		try {
			logger.debug("dynamic add or cover mapping: {}", mappingConfigurationFilePath);
			return addOrCoverMapping(XmlMappingFactory.newMappingInstance(mappingConfigurationFilePath), true);
		} catch (Exception e) {
			throw new DynamicAddMappingException(e);
		}
	}
	
	/**
	 * 动态添加映射, 如果存在则覆盖
	 * 如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * @param mappingType
	 * @param mappingConfigurationContent
	 * @return
	 * @throws DynamicAddMappingException
	 */
	public String dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent) throws DynamicAddMappingException {
		try {
			logger.debug("dynamic add or cover mapping: {}", mappingConfigurationContent);
			return addOrCoverMapping(XmlMappingFactory.newMappingInstance(mappingType, mappingConfigurationContent), true);
		} catch (Exception e) {
			throw new DynamicAddMappingException(e);
		}
	}
	
	// --------------------------------------------------------------------------------------------------------------
	/**
	 * 动态覆盖映射, 如果不存在添加
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param mappingConfigurationFilePath
	 * @return
	 * @throws DynamicCoverMappingException
	 */
	public String dynamicCoverMapping(String mappingConfigurationFilePath) throws DynamicCoverMappingException {
		try {
			logger.debug("dynamic add or cover ***simple*** mapping: {}", mappingConfigurationFilePath);
			return addOrCoverMapping(XmlMappingFactory.newMappingInstance(mappingConfigurationFilePath), false);
		} catch (Exception e) {
			throw new DynamicCoverMappingException(e);
		}
	}
	
	/**
	 * 动态覆盖映射, 如果不存在添加
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param mappingType
	 * @param mappingConfigurationContent
	 * @return
	 * @throws DynamicCoverMappingException
	 */
	public String dynamicCoverMapping(MappingType mappingType, String mappingConfigurationContent) throws DynamicCoverMappingException {
		try {
			logger.debug("dynamic add or cover ***simple*** mapping: {}", mappingConfigurationContent);
			return addOrCoverMapping(XmlMappingFactory.newMappingInstance(mappingType, mappingConfigurationContent), false);
		} catch (Exception e) {
			throw new DynamicCoverMappingException(e);
		}
	}
	
	// --------------------------------------------------------------------------------------------------------------
	/**
	 * 动态删除映射
	 * 如果是表映射, 则顺便drop表
	 * @param code
	 * @throws DynamicRemoveMappingException
	 */
	public void dynamicRemoveMapping(String code) throws DynamicRemoveMappingException {
		try {
			logger.debug("dynamic remove mapping: {}", code);
			Mapping mapping = mappingStore.removeMapping(code);
			MappingXmlConfigContext.addDropTableMapping(mapping);
		} catch (Exception e) {
			throw new DynamicRemoveMappingException(e);
		}
	}
	
	/**
	 * 动态删除映射
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param code
	 */
	public void dynamicRemoveMapping_(String code) {
		logger.debug("dynamic remove ***simple*** mapping: {}", code);
		mappingStore.removeMapping(code);
	}
	
	// --------------------------------------------------------------------------------------------------------------
	/**
	 * 是否存在指定code的映射
	 * @param code
	 * @return
	 */
	public boolean mappingExists(String code) {
		return mappingStore.mappingExists(code);
	}
	
	/**
	 * 获取映射
	 * @param code
	 * @return
	 */
	public Mapping getMapping(String code) {
		return mappingStore.getMapping(code);
	}
	
	// --------------------------------------------------------------------------------------------------------------
	@Override
	public void destroy() throws DestroyException {
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		mappingStore.destroy();
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}
}
