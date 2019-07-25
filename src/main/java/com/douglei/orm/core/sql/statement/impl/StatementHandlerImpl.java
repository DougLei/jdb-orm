package com.douglei.orm.core.sql.statement.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.statement.AbstractStatementHandler;
import com.douglei.orm.core.sql.statement.StatementExecutionException;

/**
 * java.sql.Statement的处理器
 * @author DougLei
 */
public class StatementHandlerImpl extends AbstractStatementHandler {
	private Statement statement;
	public StatementHandlerImpl(Statement statement, String sql) {
		super(sql);
		this.statement = statement;
	}

	@Override
	public List<Map<String, Object>> getQueryResultList(List<Object> parameters) throws StatementExecutionException {
		if(isExecuted()) {
			return getQueryResultList(0);
		}
		try {
			validateStatementIsClosed();
			return executeQuery(statement.executeQuery(sql));
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public Map<String, Object> getQueryUniqueResult(List<Object> parameters) throws StatementExecutionException {
		if(isExecuted()) {
			return getQueryUniqueResult(0);
		}
		try {
			validateStatementIsClosed();
			return executeUniqueQuery(statement.executeQuery(sql));
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public List<Object[]> getQueryResultList_(List<Object> parameters) throws StatementExecutionException {
		if(isExecuted()) {
			return getQueryResultList_(0);
		}
		try {
			validateStatementIsClosed();
			return executeQuery_(statement.executeQuery(sql));
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}

	@Override
	public Object[] getQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException {
		if(isExecuted()) {
			return getQueryUniqueResult_(0);
		}
		try {
			validateStatementIsClosed();
			return executeUniqueQuery_(statement.executeQuery(sql));
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public int executeUpdate(List<Object> parameters) throws StatementExecutionException {
		try {
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		}
	}
	
	@Override
	public void close() throws StatementExecutionException {
		if(!isClosed()) {
			super.close();
			closeStatement(statement);
		}
	}
}
