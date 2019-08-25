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
	private void dynamicAddMapping_(DynamicMapping entity) {
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
	 * 动态添加映射
	 * @param entity
	 */
	public synchronized void dynamicAddMapping(DynamicMapping entity) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			dynamicAddMapping_(entity);
			MappingXmlConfigContext.executeCreateTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态添加映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}
	
	/**
	 * 动态批量添加映射
	 * @param entities
	 */
	public synchronized void dynamicBatchAddMapping(List<DynamicMapping> entities) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			for (DynamicMapping entity : entities) {
				dynamicAddMapping_(entity);
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
	private void dynamicCoverMapping_(DynamicMapping entity) {
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
	 * 动态覆盖映射
	 * @param entity
	 */
	public synchronized void dynamicCoverMapping(DynamicMapping entity) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			dynamicCoverMapping_(entity);
		} catch (Exception e) {
			logger.error("动态覆盖映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}

	/**
	 * 动态批量覆盖映射
	 * @param entities
	 */
	public synchronized void dynamicBatchCoverMapping(List<DynamicMapping> entities) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			for (DynamicMapping entity : entities) {
				dynamicCoverMapping_(entity);
			}
		} catch (Exception e) {
			logger.error("动态覆盖映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}
	
	/**
	 * 动态删除映射
	 * @param mappingCode
	 */
	public synchronized void dynamicRemoveMapping(String mappingCode) {
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
	 * 动态批量删除映射
	 * @param mappingCodes
	 */
	public synchronized void dynamicBatchRemoveMapping(List<String> mappingCodes) {
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
