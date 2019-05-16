package com.douglei.sessionfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.LocalConfigurationData;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.dialect.TransactionIsolationLevel;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.session.sql.impl.SQLSessionImpl;
import com.douglei.sessions.session.table.TableSession;
import com.douglei.sessions.session.table.impl.TableSessionImpl;
import com.douglei.sessions.sqlsession.SqlSession;
import com.douglei.sessions.sqlsession.impl.SqlSessionImpl;

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
	
	private ConnectionWrapper openConnection(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return configuration.getDataSourceWrapper().getConnection(beginTransaction, transactionIsolationLevel);
	}
	
	@Override
	public void dynamicAddMapping(String mappingConfigurationContent) {
		LocalConfigurationData.setDialect(environmentProperty.getDialect());
		mappingWrapper.dynamicAddMapping(mappingConfigurationContent);
	}
	
	@Override
	public void dynamicRemoveMapping(String mappingCode) {
		mappingWrapper.dynamicRemoveMapping(mappingCode);
	}
	
	@Override
	public TableSession openTableSession() {
		return openTableSession(true);
	}

	@Override
	public TableSession openTableSession(boolean beginTransaction) {
		return openTableSession(beginTransaction, null);
	}
	
	@Override
	public TableSession openTableSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		if(logger.isDebugEnabled()) {
			logger.debug("open {} 实例, 获取connection实例, 是否开启事务: {}, 事物的隔离级别: {}", TableSessionImpl.class, beginTransaction, transactionIsolationLevel);
		}
		return new TableSessionImpl(openConnection(beginTransaction, transactionIsolationLevel), environmentProperty, mappingWrapper);
	}
	
	@Override
	public SQLSession openSQLSession() {
		return openSQLSession(true);
	}

	@Override
	public SQLSession openSQLSession(boolean beginTransaction) {
		return openSQLSession(beginTransaction, null);
	}
	
	@Override
	public SQLSession openSQLSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		if(logger.isDebugEnabled()) {
			logger.debug("open {} 实例, 获取connection实例, 是否开启事务: {}, 事物的隔离级别: {}", SQLSessionImpl.class, beginTransaction, transactionIsolationLevel);
		}
		return new SQLSessionImpl(openConnection(beginTransaction, transactionIsolationLevel), environmentProperty, mappingWrapper);
	}
	
	@Override
	public SqlSession openSqlSession() {
		return openSqlSession(true);
	}

	@Override
	public SqlSession openSqlSession(boolean beginTransaction) {
		return openSqlSession(beginTransaction, null);
	}

	@Override
	public SqlSession openSqlSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		if(logger.isDebugEnabled()) {
			logger.debug("open {} 实例, 获取connection实例, 是否开启事务: {}, 事物的隔离级别: {}", SqlSessionImpl.class, beginTransaction, transactionIsolationLevel);
		}
		return new SqlSessionImpl(openConnection(beginTransaction, transactionIsolationLevel), environmentProperty, mappingWrapper);
	}
}
