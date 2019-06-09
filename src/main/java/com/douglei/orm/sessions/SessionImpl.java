package com.douglei.orm.sessions;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.sessions.session.sql.SQLSession;
import com.douglei.orm.sessions.session.sql.impl.SQLSessionImpl;
import com.douglei.orm.sessions.session.table.TableSession;
import com.douglei.orm.sessions.session.table.impl.TableSessionImpl;
import com.douglei.orm.sessions.sqlsession.SqlSession;
import com.douglei.orm.sessions.sqlsession.impl.SqlSessionImpl;

/**
 * 
 * @author DougLei
 */
public class SessionImpl implements Session {
	private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	
	private SqlSession sqlSession;
	private TableSession tableSession;
	private SQLSession sqlSession_;

	protected boolean isClosed;
	protected ConnectionWrapper connection;
	protected EnvironmentProperty environmentProperty;
	protected MappingWrapper mappingWrapper;
	
	public SessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		this.connection = connection;
		this.environmentProperty = environmentProperty;
		this.mappingWrapper = mappingWrapper;
		DBRunEnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
	}
	
	// 验证session是否被关闭
	private void validateSessionIsClosed() {
		if(isClosed) {
			throw new SessionIsClosedException();
		}
	}
	
	@Override
	public SqlSession createSqlSession() {
		validateSessionIsClosed();
		if(sqlSession == null) {
			sqlSession = new SqlSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return sqlSession;
	}

	@Override
	public TableSession createTableSession() {
		validateSessionIsClosed();
		if(tableSession == null) {
			tableSession = new TableSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return tableSession;
	}

	@Override
	public SQLSession createSQLSession() {
		validateSessionIsClosed();
		if(sqlSession_ == null) {
			sqlSession_ = new SQLSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return sqlSession_;
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
		if(tableSession != null) {
			logger.debug("close TableSession, 将该实例 = null");
			((Session)tableSession).close();
			tableSession = null;
		}
		if(sqlSession_ != null) {
			logger.debug("close SQLSession, 将该实例 = null");
			((Session)sqlSession_).close();
			sqlSession_ = null;
		}
		if(sqlSession != null) {
			logger.debug("close SqlSession, 将该实例 = null");
			((Session)sqlSession).close();
			sqlSession = null;
		}
	}
}
