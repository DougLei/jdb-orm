package com.douglei.orm.core.sql.statement.impl;

import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.statement.AbstractStatementHandler;
import com.douglei.orm.core.sql.statement.StatementWrapperException;
import com.douglei.tools.utils.CloseUtil;

/**
 * java.sql.Statement的处理器
 * @author DougLei
 */
public class StatementHandlerImpl extends AbstractStatementHandler {
	
	private Statement statement;
	private String sql;
	
	public StatementHandlerImpl(Statement statement, String sql) {
		this.statement = statement;
		this.sql = sql;
	}

	@Override
	public List<Map<String, Object>> getQueryResultList(List<Object> parameters) {
		if(isExecuted()) {
			return getQueryResultList(0);
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeQuery(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementWrapperException(getClass().getName()+" getQueryResultList(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}
	
	@Override
	public Map<String, Object> getQueryUniqueResult(List<Object> parameters) {
		if(isExecuted()) {
			return getQueryUniqueResult(0);
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeUniqueQuery(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementWrapperException(getClass().getName()+" getQueryUniqueResult(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}
	
	@Override
	public List<Object[]> getQueryResultList_(List<Object> parameters) {
		if(isExecuted()) {
			return getQueryResultList_(0);
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeQuery_(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementWrapperException(getClass().getName()+" getQueryResultList(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}

	@Override
	public Object[] getQueryUniqueResult_(List<Object> parameters) {
		if(isExecuted()) {
			return getQueryUniqueResult_(0);
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeUniqueQuery_(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new StatementWrapperException(getClass().getName()+" getQueryUniqueResult(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}
	
	@Override
	public int executeUpdate(List<Object> parameters) {
		try {
			return statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new StatementWrapperException(getClass().getName()+" executeUpdate(List<Object>)时出现异常", e);
		}
	}
	
	@Override
	public void close() {
		if(!isClosed()) {
			super.close();
			CloseUtil.closeDBConn(statement);
		}
	}
}
