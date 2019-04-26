package com.douglei.sessionfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.session.Session;
import com.douglei.session.SqlSession;
import com.douglei.session.impl.SessionImpl;
import com.douglei.session.impl.SqlSessionImpl;

/**
 * 
 * @author DougLei
 */
public class SessionFactoryImpl implements SessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SessionFactoryImpl.class);
	
	private Configuration configuration;
	private EnvironmentProperty environmentProperty;
	private MappingWrapper mappingWrapper;
	
	public SessionFactoryImpl(Configuration configuration) {
		this.configuration = configuration;
		this.environmentProperty = configuration.getEnvironmentProperty();
		this.mappingWrapper = configuration.getMappingWrapper();
	}
	
	private ConnectionWrapper openConnection(boolean beginTransaction) {
		return configuration.getDataSourceWrapper().getConnection(beginTransaction);
	}
	
	@Override
	public Session openSession() {
		return openSession(true);
	}

	@Override
	public Session openSession(boolean beginTransaction) {
		logger.debug("open session 实例, 获取connection实例, 是否开启事务: {}", beginTransaction);
		return new SessionImpl(openConnection(beginTransaction), environmentProperty, mappingWrapper);
	}

	@Override
	public SqlSession openSqlSession() {
		return openSqlSession(true);
	}

	@Override
	public SqlSession openSqlSession(boolean beginTransaction) {
		logger.debug("open sql session 实例, 获取connection实例, 是否开启事务: {}", beginTransaction);
		return new SqlSessionImpl(openConnection(beginTransaction), environmentProperty, mappingWrapper);
	}
}
