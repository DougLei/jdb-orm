package com.douglei.orm.core.sql.statement.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.ReturnID;
import com.douglei.orm.core.sql.statement.AbstractStatementHandler;
import com.douglei.orm.core.sql.statement.InsertResult;
import com.douglei.orm.core.sql.statement.StatementExecutionException;

/**
 * java.sql.Statement的处理器
 * @author DougLei
 */
public class StatementHandlerImpl extends AbstractStatementHandler {
	private Statement statement;
	public StatementHandlerImpl(Connection connection, String sql, ReturnID returnID) throws SQLException {
		super(sql, returnID);
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
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}

	@Override
	public Map<String, Object> executeQueryUniqueResult(List<Object> parameters) throws StatementExecutionException {
		try {
			return executeUniqueQuery(statement.executeQuery(sql));
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}

	@Override
	public List<Object[]> executeQueryResultList_(List<Object> parameters) throws StatementExecutionException {
		try {
			return executeQuery_(statement.executeQuery(sql));
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}

	@Override
	public Object[] executeQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException {
		try {
			return executeUniqueQuery_(statement.executeQuery(sql));
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, e);
		} finally {
			close();
		}
	}
	
	@Override
	public InsertResult executeInsert(List<Object> parameters) throws StatementExecutionException {
		InsertResult result = new InsertResult();
		try {
			if(returnID.getOracleSeqCurrvalSQL() == null) {
				result.setRow(statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS));
				ResultSet id = statement.getGeneratedKeys();
				if(id.next())
					result.setId(id.getInt(1));
			}else {
				result.setRow(statement.executeUpdate(sql));
				result.setId(getOracleSeqCurval(statement));
			}
			return result;
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
		} finally {
			close();
		}
	}
	
	@Override
	public boolean supportCache() {
		return false;
	}
}
