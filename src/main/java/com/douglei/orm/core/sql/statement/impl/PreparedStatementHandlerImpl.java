package com.douglei.orm.core.sql.statement.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.ReturnID;
import com.douglei.orm.core.sql.statement.AbstractStatementHandler;
import com.douglei.orm.core.sql.statement.InsertResult;
import com.douglei.orm.core.sql.statement.StatementExecutionException;
import com.douglei.orm.core.sql.statement.entity.InputSqlParameter;

/**
 * java.sql.PreparedStatement的处理器
 * @author DougLei
 */
public class PreparedStatementHandlerImpl extends AbstractStatementHandler{
	private PreparedStatement preparedStatement;

	public PreparedStatementHandlerImpl(Connection connection, String sql, ReturnID returnID) throws SQLException {
		super(sql, returnID);
		this.preparedStatement = (returnID != null && returnID.getOracleSequenceName() == null) ? connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS) : connection.prepareStatement(sql) ;
	}
	
	/**
	 * 给 {@link PreparedStatement} 的占位符设置对应的参数值
	 * @param parameters
	 * @throws SQLException
	 */
	private void setParameters(List<Object> parameters) throws SQLException {
		if(parameters != null && !parameters.isEmpty()) {
			InputSqlParameter inputSqlParameter = null;
			short index = 1;
			for (Object parameter : parameters) {
				if(parameter instanceof InputSqlParameter) {
					((InputSqlParameter)parameter).setValue(index++, preparedStatement);
				}else {
					if(inputSqlParameter == null)
						inputSqlParameter = new InputSqlParameter();
					inputSqlParameter.update(parameter);
					inputSqlParameter.setValue(index++, preparedStatement);
				}
			}
		}
	}
	
	@Override
	public List<Map<String, Object>> executeQueryResultList(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeQuery(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public Map<String, Object> executeQueryUniqueResult(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeUniqueQuery(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public List<Object[]> executeQueryResultList_(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeQuery_(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}

	@Override
	public Object[] executeQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException {
		try {
			setParameters(parameters);
			return executeUniqueQuery_(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public InsertResult executeInsert(List<Object> parameters) throws StatementExecutionException {
		try {
			InsertResult result = new InsertResult();
			setParameters(parameters);
			result.setRow(preparedStatement.executeUpdate());
			
			if(returnID.getOracleSequenceName() == null) {
				ResultSet id = preparedStatement.getGeneratedKeys();
				if(id.next())
					result.setId(id.getInt(1));
			}else {
				result.setId(getOracleSeqCurval(preparedStatement.getConnection().createStatement()));
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
			setParameters(parameters);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}

	@Override
	protected Statement getStatement() {
		return preparedStatement;
	}
}
