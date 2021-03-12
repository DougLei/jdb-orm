package com.douglei.orm.sessionfactory.sessions;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.configuration.environment.datasource.TransactionIsolationLevel;
import com.douglei.orm.sessionfactory.sessions.session.sql.SQLSession;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.SQLSessionImpl;
import com.douglei.orm.sessionfactory.sessions.session.sqlquery.SQLQuerySession;
import com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl.SQLQuerySessionImpl;
import com.douglei.orm.sessionfactory.sessions.session.table.TableSession;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.TableSessionImpl;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSessionImpl;

/**
 * 
 * @author DougLei
 */
public class SessionImpl extends AbstractSession implements Session {
	private SqlSession SqlSession;
	private TableSession TableSession;
	private SQLSession SQLSession;
	private SQLQuerySession SQLQuerySession;

	public SessionImpl(ConnectionEntity connection, Environment environment) {
		super(connection, environment);
	}
	
	@Override
	public final SqlSession getSqlSession() {
		validateIsClosed();
		if(SqlSession == null) 
			SqlSession = new SqlSessionImpl(connection, environment);
		return SqlSession;
	}

	@Override
	public final TableSession getTableSession() {
		validateIsClosed();
		if(TableSession == null) 
			TableSession = new TableSessionImpl(connection, environment);
		return TableSession;
	}

	@Override
	public final SQLSession getSQLSession() {
		validateIsClosed();
		if(SQLSession == null) 
			SQLSession = new SQLSessionImpl(connection, environment);
		return SQLSession;
	}
	
	@Override
	public SQLQuerySession getSQLQuerySession() {
		validateIsClosed();
		if(SQLQuerySession == null)
			SQLQuerySession = new SQLQuerySessionImpl(connection, environment);
		return SQLQuerySession;
	}

	@Override
	public final boolean isBeginTransaction() {
		return connection.isBeginTransaction();
	}

	@Override
	public final void beginTransaction() {
		validateIsClosed();
		connection.beginTransaction();
	}
	
	@Override
	public final void updateTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
		validateIsClosed();
		connection.updateTransactionIsolationLevel(transactionIsolationLevel);
	}
	
	@Override
	public final void commit() {
		validateIsClosed();
		closeSessions();
		connection.commit();
	}

	@Override
	public final void rollback(){
		validateIsClosed();
		closeSessions();
		connection.rollback();
	}

	@Override
	public void close() {
		validateIsClosed();
		closeSessions();
		connection.close();
		isClosed = true;
	}
	
	// 关闭所有session实例
	private void closeSessions() {
		if(SqlSession != null) {
			((AbstractSession)SqlSession).close();
			SqlSession = null;
		}
		if(TableSession != null) {
			((AbstractSession)TableSession).close();
			TableSession = null;
		}
		if(SQLSession != null) {
			((AbstractSession)SQLSession).close();
			SQLSession = null;
		}
		if(SQLQuerySession != null) {
			((AbstractSession)SQLQuerySession).close();
			SQLQuerySession = null;
		}
	}
}
