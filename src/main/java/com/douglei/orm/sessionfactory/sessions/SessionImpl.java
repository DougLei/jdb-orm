package com.douglei.orm.sessionfactory.sessions;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionWrapper;
import com.douglei.orm.configuration.environment.datasource.TransactionIsolationLevel;
import com.douglei.orm.mapping.handler.MappingHandler;
import com.douglei.orm.sessionfactory.sessions.session.sql.SQLSession;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.SQLSessionImpl;
import com.douglei.orm.sessionfactory.sessions.session.table.TableSession;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.TableSessionImpl;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;
import com.douglei.orm.sessionfactory.sessions.sqlsession.impl.SqlSessionImpl;

/**
 * 
 * @author DougLei
 */
public class SessionImpl implements Session {
	private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	
	private SqlSession sqlSession;
	private TableSession TableSession;
	private SQLSession SQLSession;

	protected boolean isClosed;
	protected ConnectionWrapper connection;
	protected Environment environment;
	protected MappingHandler mappingHandler;
	
	public SessionImpl(ConnectionWrapper connection, Environment environment) {
		this.connection = connection;
		this.environment = environment;
		this.mappingHandler = environment.getMappingHandler();
		EnvironmentContext.setProperty(environment.getEnvironmentProperty());
	}
	
	// 验证session是否被关闭
	private void validateSessionIsClosed() {
		if(isClosed) 
			throw new SessionIsClosedException();
	}
	
	@Override
	public final SqlSession getSqlSession() {
		validateSessionIsClosed();
		if(sqlSession == null) 
			sqlSession = new SqlSessionImpl(connection, environment);
		return sqlSession;
	}

	@Override
	public final TableSession getTableSession() {
		validateSessionIsClosed();
		if(TableSession == null) 
			TableSession = new TableSessionImpl(connection, environment);
		return TableSession;
	}

	@Override
	public final SQLSession getSQLSession() {
		validateSessionIsClosed();
		if(SQLSession == null) 
			SQLSession = new SQLSessionImpl(connection, environment);
		return SQLSession;
	}
	
	@Override
	public final Connection getConnection() {
		validateSessionIsClosed();
		return connection.getConnection();
	}

	@Override
	public final boolean isBeginTransaction() {
		return connection.isBeginTransaction();
	}

	@Override
	public final void beginTransaction() {
		connection.beginTransaction();
	}
	
	@Override
	public final void setTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
		connection.setTransactionIsolationLevel(transactionIsolationLevel);
	}
	
	@Override
	public final void commit() {
		if(!isClosed) {
			closeSessions();
			connection.commit();
		}
	}

	@Override
	public final void rollback() {
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
		if(sqlSession != null) {
			if(logger.isDebugEnabled()) {
				logger.debug("close , 将该实例置为null", sqlSession.getClass().getName());
			}
			((Session)sqlSession).close();
			sqlSession = null;
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
			((Session)TableSession).close();
			TableSession = null;
		}
	}
}
