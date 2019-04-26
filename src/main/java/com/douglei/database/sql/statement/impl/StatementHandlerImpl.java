package com.douglei.database.sql.statement.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.sql.statement.AbstractStatementHandler;
import com.douglei.utils.CloseUtil;
import com.douglei.utils.ExceptionUtil;

/**
 * java.sql.Statement的处理器
 * @author DougLei
 */
public class StatementHandlerImpl extends AbstractStatementHandler {
	private static final Logger logger = LoggerFactory.getLogger(StatementHandlerImpl.class);
	
	private Statement statement;
	private String sql;
	
	public StatementHandlerImpl(Statement statement, String sql) {
		this.statement = statement;
		this.sql = sql;
	}

	@Override
	public List<Map<String, Object>> getQueryResultList(List<Object> parameters) {
		if(isExecuted()) {
			return getQueryResultList();
		}
		try {
			return executeQuery(statement.executeQuery(sql));
		} catch (SQLException e) {
			logger.error("{} getQueryResultList(List<Object>)时出现异常: {}", getClass(), ExceptionUtil.getExceptionDetailMessage(e));
			throw new RuntimeException(getClass()+" getQueryResultList(List<Object>)时出现异常", e);
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
