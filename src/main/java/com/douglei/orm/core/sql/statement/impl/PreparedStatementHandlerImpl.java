package com.douglei.orm.core.sql.statement.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.statement.AbstractStatementHandler;
import com.douglei.orm.core.sql.statement.StatementExecutionException;
import com.douglei.orm.core.sql.statement.entity.InputSqlParameter;
import com.douglei.tools.utils.Collections;

/**
 * java.sql.PreparedStatement的处理器
 * @author DougLei
 */
public class PreparedStatementHandlerImpl extends AbstractStatementHandler{
	private PreparedStatement preparedStatement;
	private List<List<Object>> lastParametersList; // 上一次请求参数

	public PreparedStatementHandlerImpl(PreparedStatement preparedStatement, String sql) {
		super(sql);
		this.preparedStatement = preparedStatement;
	}
	
	/**
	 * 判断参数newParameters集合，是否和属性parameters相同
	 * @param currentParameters
	 * @return -1表示没有一样的参数
	 */
	private int isSameParameters(List<Object> currentParameters) {
		if(isExecuted()) {
			int length = lastParametersList.size();
			for(int i=0;i<length;i++) {
				if(lastParametersList.get(i).equals(currentParameters)) {
					return i;
				}
			}
		}else {
			lastParametersList = new ArrayList<List<Object>>(3);
		}
		lastParametersList.add(currentParameters);
		return -1;
	}
	
	private void setParameters(List<Object> parameters) throws SQLException {
		if(parameters != null && parameters.size() > 0) {
			List<InputSqlParameter> actualParameters = turnToParameters(parameters);
			short index = 1;
			for (InputSqlParameter parameter : actualParameters) {
				parameter.setValue(index, preparedStatement);
				index++;
			}
		}
	}
	
	/**
	 * 将List<Object>转换为List<Parameter>集合
	 * @param parameters
	 * @return
	 */
	private List<InputSqlParameter> turnToParameters(List<Object> parameters){
		List<InputSqlParameter> actualParameters = new ArrayList<InputSqlParameter>(parameters.size());
		for (Object object : parameters) {
			if(object instanceof InputSqlParameter) {
				actualParameters.add((InputSqlParameter)object);
			}else {
				actualParameters.add(new InputSqlParameter(object));
			}
		}
		return actualParameters;
	}
	
	/**
	 * 获取查询的结果集合
	 * @param parameters
	 * @return
	 * @throws StatementExecutionException 
	 */
	public List<Map<String, Object>> getQueryResultList(List<Object> parameters) throws StatementExecutionException {
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryResultList(index);
		}
		try {
			validateStatementIsClosed();
			setParameters(parameters);
			return executeQuery(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public Map<String, Object> getQueryUniqueResult(List<Object> parameters) throws StatementExecutionException {
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryUniqueResult(index);
		}
		try {
			validateStatementIsClosed();
			setParameters(parameters);
			return executeUniqueQuery(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public List<Object[]> getQueryResultList_(List<Object> parameters) throws StatementExecutionException {
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryResultList_(index);
		}
		try {
			validateStatementIsClosed();
			setParameters(parameters);
			return executeQuery_(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}

	@Override
	public Object[] getQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException {
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryUniqueResult_(index);
		}
		try {
			validateStatementIsClosed();
			setParameters(parameters);
			return executeUniqueQuery_(preparedStatement.executeQuery());
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}
	
	@Override
	public int executeUpdate(List<Object> parameters) throws StatementExecutionException {
		try {
			validateStatementIsClosed();
			setParameters(parameters);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new StatementExecutionException(sql, parameters, e);
		} 
	}

	@Override
	public void close() {
		if(!isClosed()) {
			super.close();
			if(Collections.unEmpty(lastParametersList)) {
				lastParametersList.forEach(list -> list.clear());
				lastParametersList.clear();
				lastParametersList = null;
			}
			closeStatement(preparedStatement);
		}
	}
}
