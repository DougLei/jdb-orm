package com.douglei.orm.sessionfactory.dynamic.mapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
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
	private MappingWrapper mappingWrapper;
	
	public DynamicMappingProcessor(Environment environment, MappingWrapper mappingWrapper) {
		this.environment = environment;
		this.mappingWrapper = mappingWrapper;
	}

	
	// ----------------------------------------------------------------------------------------------
	private void addMapping_(DynamicMapping entity) {
		switch(entity.getType()) {
			case BY_PATH:
				entity.setCode(mappingWrapper.dynamicAddMapping(entity.getMappingConfigurationFilePath()));
				break;
			case BY_CONTENT:
				entity.setCode(mappingWrapper.dynamicAddMapping(entity.getMappingType(), entity.getMappingConfigurationContent()));
				break;
		}
	}
	
	/**
	 * 动态添加映射, 如果存在则覆盖
	 * 如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * @param entity
	 */
	public synchronized void addMapping(DynamicMapping entity) {
		try {
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
	 * 动态添加映射, 如果存在则覆盖
	 * 如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * @param entities
	 */
	public synchronized void batchAddMapping(List<DynamicMapping> entities) {
		try {
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
	 * 动态添加映射, 如果存在则覆盖
	 * 如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * @param entities
	 */
	public synchronized void batchAddMapping(DynamicMapping... entities) {
		try {
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
	
	
	// ----------------------------------------------------------------------------------------------
	private void coverMapping_(DynamicMapping entity) {
		switch(entity.getType()) {
			case BY_PATH:
				entity.setCode(mappingWrapper.dynamicCoverMapping(entity.getMappingConfigurationFilePath()));
				break;
			case BY_CONTENT:
				entity.setCode(mappingWrapper.dynamicCoverMapping(entity.getMappingType(), entity.getMappingConfigurationContent()));
				break;
		}
	}
	
	/**
	 * 动态覆盖映射, 如果不存在添加
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param entity
	 */
	public synchronized void coverMapping(DynamicMapping entity) {
		try {
			coverMapping_(entity);
		} catch (Exception e) {
			logger.error("动态覆盖映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}

	/**
	 * 动态覆盖映射, 如果不存在添加
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param entities
	 */
	public synchronized void batchCoverMapping(List<DynamicMapping> entities) {
		try {
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
	 * 动态覆盖映射, 如果不存在添加
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param entities
	 */
	public synchronized void batchCoverMapping(DynamicMapping... entities) {
		try {
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
	
	
	// ----------------------------------------------------------------------------------------------
	/**
	 * 动态删除映射
	 * 如果是表映射, 则顺便drop表
	 * @param code
	 */
	public synchronized void removeMapping(String code){
		try {
			mappingWrapper.dynamicRemoveMapping(code);
			MappingXmlConfigContext.executeDropTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlConfigContext.destroy();
		}
	}
	
	/**
	 * 动态删除映射
	 * 如果是表映射, 则顺便drop表
	 * @param codes
	 */
	public synchronized void batchRemoveMapping(List<String> codes){
		try {
			for (String code : codes) {
				mappingWrapper.dynamicRemoveMapping(code);
			}
			MappingXmlConfigContext.executeDropTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlConfigContext.destroy();
		}
	}
	
	/**
	 * 动态删除映射
	 * 如果是表映射, 则顺便drop表
	 * @param codes
	 */
	public synchronized void batchRemoveMapping(String... codes){
		try {
			for (String code : codes) {
				mappingWrapper.dynamicRemoveMapping(code);
			}
			MappingXmlConfigContext.executeDropTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlConfigContext.destroy();
		}
	}
	
	
	// ----------------------------------------------------------------------------------------------
	/**
	 * 动态删除映射
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param code
	 */
	public synchronized void removeMapping_(String code){
		try {
			mappingWrapper.dynamicRemoveMapping_(code);
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		}
	}
	
	/**
	 * 动态删除映射
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param codes
	 */
	public synchronized void batchRemoveMapping_(List<String> codes){
		try {
			for (String code : codes) {
				mappingWrapper.dynamicRemoveMapping_(code);
			}
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		}
	}
	
	/**
	 * 动态删除映射
	 * 只对映射操作, 不对实体进行任何操作, 主要是不会对表进行相关的操作
	 * @param codes
	 */
	public synchronized void batchRemoveMapping_(String... codes){
		try {
			for (String code : codes) {
				mappingWrapper.dynamicRemoveMapping_(code);
			}
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		}
	}
	
	// ----------------------------------------------------------------------------------------------
	/**
	 * 判断指定code的映射是否存在
	 * @param code
	 * @return
	 */
	public boolean mappingExists(String code) {
		return mappingWrapper.mappingExists(code);
	}
}
