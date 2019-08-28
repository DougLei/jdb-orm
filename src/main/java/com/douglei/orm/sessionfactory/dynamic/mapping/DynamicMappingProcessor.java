package com.douglei.orm.sessionfactory.dynamic.mapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.context.xml.MappingXmlReaderContext;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * 动态映射处理器
 * @author DougLei
 */
public class DynamicMappingProcessor {
	private static final Logger logger = LoggerFactory.getLogger(DynamicMappingProcessor.class);
	
	private Environment environment;
	private EnvironmentProperty environmentProperty;
	private MappingWrapper mappingWrapper;
	
	public DynamicMappingProcessor(Environment environment, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		this.environment = environment;
		this.environmentProperty = environmentProperty;
		this.mappingWrapper = mappingWrapper;
	}

	/**
	 * 动态添加映射
	 * @param entity
	 */
	private void addMapping_(DynamicMapping entity) {
		switch(entity.getType()) {
			case BY_PATH:
				entity.setMappingCode(mappingWrapper.dynamicAddMapping(entity.getMappingConfigurationFilePath()));
				break;
			case BY_CONTENT:
				entity.setMappingCode(mappingWrapper.dynamicAddMapping(entity.getMappingType(), entity.getMappingConfigurationContent()));
				break;
		}
	}
	
	/**
	 * <pre>
	 * 动态添加映射, 如果存在则覆盖
	 * 如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param entity
	 */
	public synchronized void addMapping(DynamicMapping entity) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			addMapping_(entity);
			MappingXmlConfigContext.executeCreateTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态添加映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}
	
	/**
	 * <pre>
	 * 动态批量添加映射, 如果存在则覆盖
	 * 如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param entities
	 */
	public synchronized void batchAddMapping(List<DynamicMapping> entities) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			for (DynamicMapping entity : entities) {
				addMapping_(entity);
			}
			MappingXmlConfigContext.executeCreateTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态添加映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}
	
	/**
	 * 动态覆盖映射
	 * @param entity
	 */
	private void coverMapping_(DynamicMapping entity) {
		switch(entity.getType()) {
			case BY_PATH:
				entity.setMappingCode(mappingWrapper.dynamicCoverMapping(entity.getMappingConfigurationFilePath()));
				break;
			case BY_CONTENT:
				entity.setMappingCode(mappingWrapper.dynamicCoverMapping(entity.getMappingType(), entity.getMappingConfigurationContent()));
				break;
		}
	}
	
	/**
	 * <pre>
	 * 动态覆盖映射, 如果不存在添加
	 * 只对映射操作
	 * 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * </pre>
	 * @param entity
	 */
	public synchronized void coverMapping(DynamicMapping entity) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			coverMapping_(entity);
		} catch (Exception e) {
			logger.error("动态覆盖映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}

	/**
	 * <pre>
	 * 动态批量覆盖映射, 如果不存在添加
	 * 只对映射操作
	 * 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * </pre>
	 * @param entities
	 */
	public synchronized void batchCoverMapping(List<DynamicMapping> entities) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			for (DynamicMapping entity : entities) {
				coverMapping_(entity);
			}
		} catch (Exception e) {
			logger.error("动态覆盖映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}
	
	/**
	 * <pre>
	 * 动态删除映射
	 * 如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCode
	 */
	public synchronized void removeMapping(String mappingCode){
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			mappingWrapper.dynamicRemoveMapping(mappingCode);
			MappingXmlConfigContext.executeDropTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlConfigContext.destroy();
		}
	}
	
	/**
	 * <pre>
	 * 动态批量删除映射
	 * 如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCodes
	 */
	public synchronized void batchRemoveMapping(List<String> mappingCodes){
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			for (String mappingCode : mappingCodes) {
				mappingWrapper.dynamicRemoveMapping(mappingCode);
			}
			MappingXmlConfigContext.executeDropTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlConfigContext.destroy();
		}
	}
}
