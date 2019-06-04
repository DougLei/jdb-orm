package com.douglei.sessions;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
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
public class SessionImpl implements Session {
	private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	
	private SqlSession sqlSession;
	private TableSession tableSession;
	private SQLSession sqlSession_;
	
	protected ConnectionWrapper connection;
	protected EnvironmentProperty environmentProperty;
	protected MappingWrapper mappingWrapper;
	
	public SessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		this.connection = connection;
		this.environmentProperty = environmentProperty;
		this.mappingWrapper = mappingWrapper;
	}
	
	@Override
	public SqlSession createSqlSession() {
		if(sqlSession == null) {
			sqlSession = new SqlSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return sqlSession;
	}

	@Override
	public TableSession createTableSession() {
		if(tableSession == null) {
			tableSession = new TableSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return tableSession;
	}

	@Override
	public SQLSession createSQLSession() {
		if(sqlSession_ == null) {
			sqlSession_ = new SQLSessionImpl(connection, environmentProperty, mappingWrapper);
		}
		return sqlSession_;
	}
	
	@Override
	public Connection getConnection() {
		return connection.getConnection();
	}

	@Override
	public void commit() {
		closeSessions();
		connection.commit();
	}

	@Override
	public void rollback() {
		closeSessions();
		connection.rollback();
	}

	@Override
	public void close() {
		closeSessions();
		if(!connection.isFinishTransaction()) {
			logger.info("当前[{}]的事物没有处理结束: commit 或 rollback, 程序默认进行 commit操作", getClass().getName());
			connection.commit();
		}
		connection.close();
	}
	
	private boolean isCloseSessions;// 是否关闭所有session
	private void closeSessions() {// 关闭所有session
		if(isCloseSessions) {
			return;
		}
		isCloseSessions = true;
		if(tableSession != null) {
			tableSession.close();
		}
		if(sqlSession_ != null) {
			sqlSession_.close();
		}
		if(sqlSession != null) {
			sqlSession.close();
		}
	}
}
