package com.douglei.orm.factory.sessions;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.factory.sessions.session.sql.SQLSession;
import com.douglei.orm.factory.sessions.session.sql.impl.SQLSessionImpl;
import com.douglei.orm.factory.sessions.session.table.TableSession;
import com.douglei.orm.factory.sessions.session.table.impl.TableSessionImpl;
import com.douglei.orm.factory.sessions.sqlsession.SqlSession;
import com.douglei.orm.factory.sessions.sqlsession.impl.SqlSessionImpl;

/**
 * 
 * @author DougLei
 */
public class SessionImpl implements Session {
	private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	
	private SqlSession SqlSession;
	private TableSession TableSession;
	private SQLSession SQLSession;

	protected boolean isClosed;
	protected ConnectionWrapper connection;
	protected EnvironmentProperty environmentProperty;
	protected MappingWrapper mappingWrapper;
	
	public SessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		this.connection = connection;
		this.environmentProperty = environmentProperty;
		this.mappingWrapper = mappingWrapper;
		EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
	}
	
	// 验证session是否被关闭
	private void validateSessionIsClosed() {
		if(isClosed) {
			throw new SessionIsClosedException();
		}
	}
	
	@Override
	public SqlSession getSqlSession() {
		validateSessionIsClosed();
		if(SqlSession == null) {
			SqlSession = new SqlSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return SqlSession;
	}

	@Override
	public TableSession getTableSession() {
		validateSessionIsClosed();
		if(TableSession == null) {
			TableSession = new TableSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return TableSession;
	}

	@Override
	public SQLSession getSQLSession() {
		validateSessionIsClosed();
		if(SQLSession == null) {
			SQLSession = new SQLSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return SQLSession;
	}
	
	@Override
	public Connection getConnection() {
		validateSessionIsClosed();
		return connection.getConnection();
	}

	@Override
	public boolean isBeginTransaction() {
		return connection.isBeginTransaction();
	}

	@Override
	public void beginTransaction() {
		connection.beginTransaction();
	}
	
	@Override
	public void setTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
		connection.setTransactionIsolationLevel(transactionIsolationLevel);
	}
	
	@Override
	public void commit() {
		if(!isClosed) {
			closeSessions();
			connection.commit();
		}
	}

	@Override
	public void rollback() {
		if(!isClosed) {
			closeSessions();
			connection.rollback();
		}
	}

	@Override
	public void close() {
		if(!isClosed) {
			closeSessions();
			if(!connection.isFinishTransaction()) {
				if(logger.isDebugEnabled()) {
					logger.debug("当前[{}]的事物没有处理结束: commit 或 rollback, 程序默认进行 commit操作", getClass().getName());
				}
				connection.commit();
			}
			connection.close();
			isClosed = true;
		}
	}
	
	private void closeSessions() {
		if(SqlSession != null) {
			if(logger.isDebugEnabled()) {
				logger.debug("close , 将该实例置为null", SqlSession.getClass().getName());
			}
			((Session)SqlSession).close();
			SqlSession = null;
		}
		if(SQLSession != null) {
			if(logger.isDebugEnabled()) {
				logger.debug("close , 将该实例置为null", SQLSession.getClass().getName());
			}
			((Session)SQLSession).close();
			SQLSession = null;
		}
		if(TableSession != null) {
			if(logger.isDebugEnabled()) {
				logger.debug("close , 将该实例置为null", TableSession.getClass().getName());
			}
			
			try {
				((Session)TableSession).close();
			} catch (ExecutionException e) {
				throw e;
			} finally {
				TableSession = null;
			}
		}
	}
}
