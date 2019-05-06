package com.douglei.database.sql.statement.impl;

import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.douglei.database.sql.statement.AbstractStatementHandler;
import com.douglei.utils.CloseUtil;

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
	public List<Map<String, Object>> getQueryResultList(List<? extends Object> parameters) {
		if(isExecuted()) {
			return getQueryResultList();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeQuery(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new RuntimeException(getClass()+" getQueryResultList(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}
	
	@Override
	public Map<String, Object> getQueryUniqueResult(List<? extends Object> parameters) {
		if(isExecuted()) {
			return getQueryUniqueResult();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeUniqueQuery(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new RuntimeException(getClass()+" getQueryUniqueResult(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}
	
	@Override
	public List<Object[]> getQueryResultList_(List<? extends Object> parameters) {
		if(isExecuted()) {
			return getQueryResultList_();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeQuery_(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new RuntimeException(getClass()+" getQueryResultList(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}

	@Override
	public Object[] getQueryUniqueResult_(List<? extends Object> parameters) {
		if(isExecuted()) {
			return getQueryUniqueResult_();
		}
		try {
			if(isClosed()) {
				throw new Exception("无法执行, 连接已经关闭");
			}
			return executeUniqueQuery_(statement.executeQuery(sql));
		} catch (Exception e) {
			throw new RuntimeException(getClass()+" getQueryUniqueResult(List<Object>)时出现异常", e);
		} finally {
			close();
		}
	}
	
	@Override
	public int executeUpdate(List<? extends Object> parameters) {
		try {
			return statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new RuntimeException(getClass()+" executeUpdate(List<Object>)时出现异常", e);
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
