package com.douglei.orm.sessionfactory.sessions;

import java.sql.Connection;

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
	private SqlSession SqlSession;
	private TableSession TableSession;
	private SQLSession SQLSession;

	protected boolean isClosed; // session是否关闭
	protected ConnectionWrapper connection;
	protected MappingHandler mappingHandler;
	
	public SessionImpl(ConnectionWrapper connection) {
		this.connection = connection;
	}
	
	// 验证session是否被关闭
	private void validateSessionIsClosed() {
		if(isClosed) 
			throw new SessionIsClosedException();
	}
	
	@Override
	public final SqlSession getSqlSession() {
		validateSessionIsClosed();
		if(SqlSession == null) 
			SqlSession = new SqlSessionImpl(connection);
		return SqlSession;
	}

	@Override
	public final TableSession getTableSession() {
		validateSessionIsClosed();
		if(TableSession == null) 
			TableSession = new TableSessionImpl(connection);
		return TableSession;
	}

	@Override
	public final SQLSession getSQLSession() {
		validateSessionIsClosed();
		if(SQLSession == null) 
			SQLSession = new SQLSessionImpl(connection);
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
		validateSessionIsClosed();
		connection.beginTransaction();
	}
	
	@Override
	public final void updateTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
		validateSessionIsClosed();
		connection.updateTransactionIsolationLevel(transactionIsolationLevel);
	}
	
	@Override
	public final void commit() {
		validateSessionIsClosed();
		closeSessions();
		connection.commit();
	}

	@Override
	public final void rollback(){
		validateSessionIsClosed();
		closeSessions();
		connection.rollback();
	}

	@Override
	public void close() {
		validateSessionIsClosed();
		closeSessions();
		connection.close();
		isClosed = true;
	}
	
	// 关闭所有session实例
	private void closeSessions() {
		if(SqlSession != null) {
			((Session)SqlSession).close();
			SqlSession = null;
		}
		if(SQLSession != null) {
			((Session)SQLSession).close();
			SQLSession = null;
		}
		if(TableSession != null) {
			((Session)TableSession).close();
			TableSession = null;
		}
	}
}
