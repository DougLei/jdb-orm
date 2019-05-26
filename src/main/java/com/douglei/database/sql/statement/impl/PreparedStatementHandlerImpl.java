package com.douglei.database.sql.statement.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.database.sql.statement.AbstractStatementHandler;
import com.douglei.database.sql.statement.entity.InputSqlParameter;
import com.douglei.utils.CloseUtil;

/**
 * java.sql.PreparedStatement的处理器
 * @author DougLei
 */
public class PreparedStatementHandlerImpl extends AbstractStatementHandler{
	
	private PreparedStatement preparedStatement;
	private List<Object> parameters;

	public PreparedStatementHandlerImpl(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
	
	/**
	 * 判断参数newParameters集合，是否和属性parameters相同
	 * @param newParameters
	 * @return
	 */
	private boolean isSameParameters(List<Object> newParameters) {
		if(parameters == newParameters) {
			return true;
		}
		if(parameters != null) {
			return parameters.equals(newParameters);
		}
		if(newParameters != null) {
			return newParameters.equals(parameters);
		}
		return false;
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
	 */
	public List<Map<String, Object>> getQueryResultList(List<Object> parameters) {
		if(isExecuted() && isSameParameters(parameters)) {
			return getQueryResultList();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			setParameters(parameters);
			return executeQuery(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new RuntimeException(getClass().getName()+" getQueryResultList(List<Object>)时出现异常", e);
		} 
	}
	
	@Override
	public Map<String, Object> getQueryUniqueResult(List<Object> parameters) {
		if(isExecuted() && isSameParameters(parameters)) {
			return getQueryUniqueResult();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			setParameters(parameters);
			return executeUniqueQuery(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new RuntimeException(getClass().getName()+" getQueryUniqueResult(List<Object>)时出现异常", e);
		} 
	}
	
	@Override
	public List<Object[]> getQueryResultList_(List<Object> parameters) {
		if(isExecuted() && isSameParameters(parameters)) {
			return getQueryResultList_();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			setParameters(parameters);
			return executeQuery_(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new RuntimeException(getClass().getName()+" getQueryResultList(List<Object>)时出现异常", e);
		} 
	}

	@Override
	public Object[] getQueryUniqueResult_(List<Object> parameters) {
		if(isExecuted() && isSameParameters(parameters)) {
			return getQueryUniqueResult_();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			setParameters(parameters);
			return executeUniqueQuery_(preparedStatement.executeQuery());
		} catch (Exception e) {
			throw new RuntimeException(getClass().getName()+" getQueryUniqueResult(List<Object>)时出现异常", e);
		} 
	}
	
	@Override
	public int executeUpdate(List<Object> parameters) {
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			setParameters(parameters);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(getClass().getName()+" executeUpdate(List<Object>)时出现异常", e);
		} 
	}

	/**
	 * 关闭statementHandler
	 */
	public void close() {
		if(!isClosed()) {
			super.close();
			if(parameters != null && parameters.size() > 0) {
				parameters.clear();
			}
			CloseUtil.closeDBConn(preparedStatement);
		}
	}
}
