package com.douglei.orm.sql.statement.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * java.sql.PreparedStatement的处理器
 * @author DougLei
 */
public class PreparedStatementHandlerImpl extends AbstractStatementHandler{
	private PreparedStatement preparedStatement;

	public PreparedStatementHandlerImpl(Connection connection, String sql, AutoIncrementID autoIncrementID) throws SQLException {
		super(sql, autoIncrementID);
		this.preparedStatement = 
				(autoIncrementID!=null && !EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getName().equals(DatabaseNameConstants.ORACLE)) 
						?connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS):connection.prepareStatement(sql) ;
	}
	
	/**
	 * 给 {@link PreparedStatement} 的占位符设置对应的参数值
	 * @param parameters
	 * @throws SQLException
	 */
	private void setParameters(List<Object> parameters) throws SQLException {
		if(parameters != null && !parameters.isEmpty()) {
			short index = 1;
			for (Object parameter : parameters) {
				if(parameter instanceof InputSqlParameter) {
					((InputSqlParameter)parameter).setValue(index++, preparedStatement);
				}else {
					new InputSqlParameter(parameter).setValue(index++, preparedStatement);
				}
			}
		}
	}
	
	@Override
	public List<Map<String, Object>> executeQueryResultList(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeQuery(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public List<Map<String, Object>> executeLimitQueryResultList(int startRow, int length, List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeLimitQuery(startRow, length, preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public Map<String, Object> executeQueryUniqueResult(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeUniqueQuery(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public List<Object[]> executeQueryResultList_(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeQuery_(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}

	@Override
	public List<Object[]> executeLimitQueryResultList_(int startRow, int length, List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeLimitQuery_(startRow, length, preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public Object[] executeQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeUniqueQuery_(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public InsertResult executeInsert(List<Object> parameters) throws StatementExecutionException {
		try {
			InsertResult result = new InsertResult();
			setParameters(parameters);
			result.setRow(preparedStatement.executeUpdate());
			
			if(EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getName().equals(DatabaseNameConstants.ORACLE)) {
				result.setAutoIncrementIDValue(getOracleSeqCurrval(preparedStatement.getConnection().createStatement()));
			}else {
				ResultSet id = preparedStatement.getGeneratedKeys();
				if(id.next())
					result.setAutoIncrementIDValue(id.getInt(1));
			}
			return result;
		} catch (Exception e) {
			throw new StatementExecutionException(sql, e);
		}
	}
	
	@Override
	public int executeUpdate(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}

	@Override
	protected Statement getStatement() {
		return preparedStatement;
	}
}
