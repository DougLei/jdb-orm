package com.douglei.orm.sessionfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.sessionfactory.mapping.MappingProcessor;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.orm.sessionfactory.sessions.SessionImpl;
import com.douglei.orm.sessionfactory.validator.DataValidator;

/**
 * 
 * @author DougLei
 */
public class SessionFactoryImpl implements SessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SessionFactoryImpl.class);
	
	private Configuration configuration;
	private Environment environment;
	private EnvironmentProperty environmentProperty;
	
	private MappingProcessor mappingProcessor;
	private DataValidator dataValidator;
	
	public SessionFactoryImpl(Configuration configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
		this.environmentProperty = environment.getEnvironmentProperty();
	}
	
	private ConnectionWrapper getConnectionWrapper(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return environment.getDataSourceWrapper().getConnection(beginTransaction, transactionIsolationLevel);
	}
	
	@Override
	public String getId() {
		return configuration.getId();
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
		return new SessionImpl(getConnectionWrapper(beginTransaction, transactionIsolationLevel), environmentProperty);
	}
	
	@Override
	public MappingProcessor getMappingProcessor() {
		if(mappingProcessor == null) 
			mappingProcessor = new MappingProcessor(environment.getMappingHandler());
		
		EnvironmentContext.setProperty(environmentProperty);
		return mappingProcessor;
	}
	
	@Override
	public DataValidator getDataValidator() {
		if(dataValidator == null) 
			dataValidator = new DataValidator(environmentProperty.getMappingContainer());

		EnvironmentContext.setProperty(environmentProperty);
		return dataValidator;
	}

	@Override
	public void destroy() {
		mappingProcessor = null;
		dataValidator = null;
		if(configuration != null) {
			configuration.destroy();
			configuration = null;
		}
	}
}
