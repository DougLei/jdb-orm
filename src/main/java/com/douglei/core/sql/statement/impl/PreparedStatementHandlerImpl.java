package com.douglei.core.sql.statement.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.core.sql.statement.AbstractStatementHandler;
import com.douglei.core.sql.statement.entity.InputSqlParameter;
import com.douglei.utils.CloseUtil;

/**
 * java.sql.PreparedStatement的处理器
 * @author DougLei
 */
public class PreparedStatementHandlerImpl extends AbstractStatementHandler{
	
	private PreparedStatement preparedStatement;
	private List<List<Object>> lastParametersList; // 上一次请求参数

	public PreparedStatementHandlerImpl(PreparedStatement preparedStatement) {
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
	 */
	public List<Map<String, Object>> getQueryResultList(List<Object> parameters) {
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryResultList(index);
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
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryUniqueResult(index);
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
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryResultList_(index);
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
		int index = isSameParameters(parameters);
		if(index > -1) {
			return getQueryUniqueResult_(index);
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
			if(lastParametersList != null) {
				for (List<Object> list : lastParametersList) {
					list.clear();
				}
				lastParametersList.clear();
				lastParametersList = null;
			}
			CloseUtil.closeDBConn(preparedStatement);
		}
	}
}
