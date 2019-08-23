package com.douglei.orm.sessionfactory.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.MappingXmlReaderContext;
import com.douglei.orm.context.MappingConfigContext;
import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.sessionfactory.DynamicMapping;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.sessions.Session;
import com.douglei.orm.sessions.SessionImpl;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public class SessionFactoryImpl implements SessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SessionFactoryImpl.class);
	
	private Configuration configuration;
	private Environment environment;
	private EnvironmentProperty environmentProperty;
	private MappingWrapper mappingWrapper;
	
	public SessionFactoryImpl(Configuration configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
		this.environmentProperty = environment.getEnvironmentProperty();
		this.mappingWrapper = environment.getMappingWrapper();
	}
	
	private ConnectionWrapper getConnectionWrapper(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return environment.getDataSourceWrapper().getConnection(beginTransaction, transactionIsolationLevel);
	}
	
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
	
	@Override
	public synchronized void dynamicAddMapping(DynamicMapping entity) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			dynamicAddMapping_(entity);
			MappingConfigContext.executeCreateTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态添加映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}
	
	@Override
	public synchronized void dynamicBatchAddMapping(List<DynamicMapping> entities) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			for (DynamicMapping entity : entities) {
				dynamicAddMapping_(entity);
			}
			MappingConfigContext.executeCreateTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态添加映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingXmlReaderContext.destroy();
		}
	}
	
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
	
	@Override
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

	@Override
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
	
	@Override
	public synchronized void dynamicRemoveMapping(String mappingCode) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			mappingWrapper.dynamicRemoveMapping(mappingCode);
			MappingConfigContext.executeDropTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingConfigContext.destroy();
		}
	}
	
	@Override
	public synchronized void dynamicBatchRemoveMapping(List<String> mappingCodes) {
		try {
			EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
			for (String mappingCode : mappingCodes) {
				mappingWrapper.dynamicRemoveMapping(mappingCode);
			}
			MappingConfigContext.executeDropTable(environment.getDataSourceWrapper());
		} catch (Exception e) {
			logger.error("动态删除映射时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw e;
		} finally {
			MappingConfigContext.destroy();
		}
	}
	
	@Override
	public Session openSession() {
		return openSession(true);
	}

	@Override
	public Session openSession(boolean beginTransaction) {
		return openSession(beginTransaction, null);
	}

	@Override
	public Session openSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		if(logger.isDebugEnabled()) {
			logger.debug("open {} 实例, 获取connection实例, 是否开启事务: {}, 事物的隔离级别: {}", SessionImpl.class, beginTransaction, transactionIsolationLevel);
		}
		return new SessionImpl(getConnectionWrapper(beginTransaction, transactionIsolationLevel), environmentProperty, mappingWrapper);
	}
	
	@Override
	public TableSqlStatementHandler getTableSqlStatementHandler() {
		return environmentProperty.getDialect().getTableSqlStatementHandler();
	}

	@Override
	public void destroy() {
		configuration.destroy();
		configuration = null;
	}

	@Override
	public String getId() {
		return configuration.getId();
	}
}
