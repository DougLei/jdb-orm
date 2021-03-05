package com.douglei.orm.sql.statement.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.DatabaseNameConstants;
import com.douglei.orm.sql.AutoIncrementID;
import com.douglei.orm.sql.statement.AbstractStatementHandler;
import com.douglei.orm.sql.statement.InsertResult;
import com.douglei.orm.sql.statement.StatementExecutionException;

/**
 * java.sql.Statement的处理器
 * @author DougLei
 */
public class StatementHandlerImpl extends AbstractStatementHandler {
	private Statement statement;
	public StatementHandlerImpl(Connection connection, String sql, AutoIncrementID autoIncrementID) throws SQLException {
		super(sql, autoIncrementID);
		this.statement = connection.createStatement();
	}
	
	@Override
	protected Statement getStatement() {
		return statement;
	}
	
	@Override
	public List<Map<String, Object>> executeQueryResultList(List<Object> parameters) throws StatementExecutionException {
		try {
			return executeQuery(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public List<Map<String, Object>> executeLimitQueryResultList(int startRow, int length, List<Object> parameters) throws StatementExecutionException {
		try {
			return executeLimitQuery(startRow, length, statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}

	@Override
	public Map<String, Object> executeQueryUniqueResult(List<Object> parameters) throws StatementExecutionException {
		try {
			return executeUniqueQuery(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}

	@Override
	public List<Object[]> executeQueryResultList_(List<Object> parameters) throws StatementExecutionException {
		try {
			return executeQuery_(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public List<Object[]> executeLimitQueryResultList_(int startRow, int length, List<Object> parameters) throws StatementExecutionException {
		try {
			return executeLimitQuery_(startRow, length, statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}

	@Override
	public Object[] executeQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException {
		try {
			return executeUniqueQuery_(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public InsertResult executeInsert(List<Object> parameters) throws StatementExecutionException {
		try {
			InsertResult result = new InsertResult();
			if(EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getName().equals(DatabaseNameConstants.ORACLE)) {
				result.setRow(statement.executeUpdate(sql));
				result.setAutoIncrementIDValue(getOracleSeqCurrval(statement));
			}else {
				result.setRow(statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS));
				ResultSet id = statement.getGeneratedKeys();
				if(id.next())
					result.setAutoIncrementIDValue(id.getInt(1));
			}
			return result;
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public int executeUpdate(List<Object> parameters) throws StatementExecutionException {
		try {
			return statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public boolean supportCache() {
		return false;
	}
}
