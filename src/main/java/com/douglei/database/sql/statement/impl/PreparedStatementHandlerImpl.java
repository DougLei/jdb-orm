package com.douglei.database.sql.statement.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.sql.statement.AbstractStatementHandler;
import com.douglei.utils.CloseUtil;
import com.douglei.utils.ExceptionUtil;

/**
 * java.sql.PreparedStatement的处理器
 * @author DougLei
 */
public class PreparedStatementHandlerImpl extends AbstractStatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(PreparedStatementHandlerImpl.class);
	
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
			List<Parameter> actualParameters = turnToParameters(parameters);
			int index = 1;
			for (Parameter parameter : actualParameters) {
				parameter.setValue(index, preparedStatement);
				index++;
			}
		}
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
			logger.error("{} getQueryResultList(List<Object>)时出现异常: {}", getClass(), ExceptionUtil.getExceptionDetailMessage(e));
			throw new RuntimeException(getClass()+" getQueryResultList(List<Object>)时出现异常", e);
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
			logger.error("{} executeUpdate(List<Object>)时出现异常: {}", getClass(), ExceptionUtil.getExceptionDetailMessage(e));
			throw new RuntimeException(getClass()+" executeUpdate(List<Object>)时出现异常", e);
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
