package com.douglei.orm.configuration.environment.mapping;

import java.util.List;

import org.dom4j.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.tools.instances.scanner.FileScanner;

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
	 * 扫描映射文件
	 * @param fileScanner
	 * @param paths
	 */
	protected void scanMappingFiles(FileScanner fileScanner, List<Attribute> paths) {
		if(paths != null) {
			StringBuilder path = new StringBuilder(paths.size() * 20);
			paths.forEach(p -> {
				path.append(",").append(p.getValue());
			});
			fileScanner.multiScan(searchAll, path.substring(1).split(","));
		}
	}
	
	/**
	 * <pre>
	 * 	添加映射
	 * 	如果是表映射, 则顺便create表
	 * 	这个方法只在框架启动时使用, 用来加载已经配置的xml映射
	 * </pre>
	 * @return mapping的code
	 */
	protected String addMapping(Mapping mapping){
		mappingStore.addMapping(mapping);
		MappingXmlConfigContext.addCreateTableMapping(mapping);
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
		mappingStore.addOrCoverMapping(mapping);
		MappingXmlConfigContext.addCreateTableMapping(mapping);
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
		mappingStore.addOrCoverMapping(mapping);
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
		Mapping mapping = mappingStore.removeMapping(mappingCode);
		MappingXmlConfigContext.addDropTableMapping(mapping);
	}
	
	/**
	 * <pre>
	 * 	删除映射
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param mappingCode
	 */
	protected void removeMapping_(String mappingCode) {
		mappingStore.removeMapping(mappingCode);
	}
	
	/**
	 * 获取映射
	 * @param mappingCode
	 * @return
	 */
	public Mapping getMapping(String mappingCode) {
		return mappingStore.getMapping(mappingCode);
	}
	
	/**
	 * 是否存在指定code的映射
	 * @param mappingCode
	 * @return
	 */
	public boolean mappingExists(String mappingCode) {
		return mappingStore.mappingExists(mappingCode);
	}
	
	
	/**
	 * <pre>
	 * 	动态添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param mappingConfigurationFilePath 配置文件路径
	 * @return mapping的code
	 * @throws DynamicAddMappingException
	 */
	public abstract String dynamicAddMapping(String mappingConfigurationFilePath) throws DynamicAddMappingException;
	/**
	 * <pre>
	 * 	动态添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent 配置内容
	 * @return mapping的code
	 * @throws DynamicAddMappingException
	 */
	public abstract String dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent) throws DynamicAddMappingException;
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param mappingConfigurationFilePath 配置文件路径
	 * @return mapping的code
	 * @throws DynamicCoverMappingException
	 */
	public abstract String dynamicCoverMapping(String mappingConfigurationFilePath) throws DynamicCoverMappingException;
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent 配置内容
	 * @return mapping的code
	 * @throws DynamicCoverMappingException
	 */
	public abstract String dynamicCoverMapping(MappingType mappingType, String mappingConfigurationContent) throws DynamicCoverMappingException;
	/**
	 * <pre>
	 * 	动态删除映射
	 * 	如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCode
	 * @throws DynamicRemoveMappingException
	 */
	public abstract void dynamicRemoveMapping(String mappingCode) throws DynamicRemoveMappingException;
	/**
	 * <pre>
	 * 	动态删除映射
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param mappingCode
	 * @throws DynamicRemoveMappingException
	 */
	public abstract void dynamicRemoveMapping_(String mappingCode) throws DynamicRemoveMappingException;
	
	
	@Override
	public void destroy() throws DestroyException {
		logger.debug("{} 开始 destroy", getClass());
		mappingStore.destroy();
		logger.debug("{} 结束 destroy", getClass());
	}
}
