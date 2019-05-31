package com.douglei.sessionfactory;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.context.RunMappingConfigurationContext;
import com.douglei.core.dialect.TransactionIsolationLevel;
import com.douglei.core.dialect.db.database.DatabaseSqlStatementHandler;
import com.douglei.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.core.sql.ConnectionWrapper;
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
	
	private ConnectionWrapper getConnectionWrapper(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return configuration.getDataSourceWrapper().getConnection(beginTransaction, transactionIsolationLevel);
	}
	
	@Override
	public void dynamicAddMapping(DynamicMapping entity) {
		DBRunEnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
		mappingWrapper.dynamicAddMapping(entity.getMappingType(), entity.getMappingConfigurationContent());
		RunMappingConfigurationContext.executeCreateTable(configuration.getDataSourceWrapper());
	}
	
	@Override
	public void dynamicBatchAddMapping(List<DynamicMapping> entities) {
		DBRunEnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
		for (DynamicMapping entity : entities) {
			mappingWrapper.dynamicAddMapping(entity.getMappingType(), entity.getMappingConfigurationContent());
		}
		RunMappingConfigurationContext.executeCreateTable(configuration.getDataSourceWrapper());
	}

	@Override
	public void dynamicCoverMapping(DynamicMapping entity) {
		mappingWrapper.dynamicCoverMapping(entity.getMappingType(), entity.getMappingConfigurationContent());
	}

	@Override
	public void dynamicBatchCoverMapping(List<DynamicMapping> entities) {
		for (DynamicMapping dynamicMapping : entities) {
			dynamicCoverMapping(dynamicMapping);
		}
	}

	@Override
	public void dynamicRemoveMapping(String mappingCode) {
		mappingWrapper.removeMapping(mappingCode);
		RunMappingConfigurationContext.executeDropTable(configuration.getDataSourceWrapper());
	}
	
	@Override
	public void dynamicBatchRemoveMapping(List<String> mappingCodes) {
		for (String mappingCode : mappingCodes) {
			mappingWrapper.removeMapping(mappingCode);
		}
		RunMappingConfigurationContext.executeDropTable(configuration.getDataSourceWrapper());
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
		return new TableSessionImpl(getConnectionWrapper(beginTransaction, transactionIsolationLevel), environmentProperty, mappingWrapper);
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
		return new SQLSessionImpl(getConnectionWrapper(beginTransaction, transactionIsolationLevel), environmentProperty, mappingWrapper);
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
		return new SqlSessionImpl(getConnectionWrapper(beginTransaction, transactionIsolationLevel), environmentProperty, mappingWrapper);
	}

	@Override
	public Connection openConnection() {
		return getConnectionWrapper(false, null).getConnection();
	}

	@Override
	public Connection openConnection(boolean beginTransaction) {
		return getConnectionWrapper(beginTransaction, null).getConnection();
	}

	@Override
	public Connection openConnection(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return getConnectionWrapper(beginTransaction, transactionIsolationLevel).getConnection();
	}

	@Override
	public DatabaseSqlStatementHandler getDatabaseSqlStatementHandler() {
		return environmentProperty.getDialect().getDatabaseSqlStatementHandler();
	}

	@Override
	public TableSqlStatementHandler getTableSqlStatementHandler() {
		return environmentProperty.getDialect().getTableSqlStatementHandler();
	}
}
