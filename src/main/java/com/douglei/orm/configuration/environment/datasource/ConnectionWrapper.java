package com.douglei.orm.configuration.environment.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.sql.AutoIncrementID;
import com.douglei.orm.sql.statement.StatementHandler;
import com.douglei.orm.sql.statement.impl.PreparedStatementHandlerImpl;
import com.douglei.orm.sql.statement.impl.StatementHandlerImpl;
import com.douglei.tools.ExceptionUtil;

/**
 * java.sql.Connection 包装类
 * @author DougLei
 */
public class ConnectionWrapper {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionWrapper.class);
	private boolean isBeginTransaction; // 是否开启事物
	private TransactionIsolationLevel transactionIsolationLevel; // 事物的隔离级别
	private Connection connection;
	
	public ConnectionWrapper(DataSource dataSource, boolean isBeginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		if(logger.isDebugEnabled())
			logger.debug("实例化{}", getClass().getName());
		
		try {
			this.connection = dataSource.getConnection();
			setIsBeginTransaction(isBeginTransaction);
			this.transactionIsolationLevel = transactionIsolationLevel;
			if(isBeginTransaction && transactionIsolationLevel != null && transactionIsolationLevel != TransactionIsolationLevel.DEFAULT) 
				this.connection.setTransactionIsolation(transactionIsolationLevel.getLevel());
		} catch (Exception e) {
			throw new ConnectionException("获取Connection时出现异常", e);
		}
	}
	
	// 设置是否开启事务
	private void setIsBeginTransaction(boolean isBeginTransaction) throws SQLException {
		if(this.isBeginTransaction && isBeginTransaction) 
			return;
		
		this.isBeginTransaction = isBeginTransaction;
		if(isBeginTransaction) {
			if(connection.getAutoCommit()) 
				connection.setAutoCommit(false);
		}else {
			if(!connection.getAutoCommit()) 
				connection.setAutoCommit(true);
		}
	}
	
	/**
	 * 获取数据库原生连接实例
	 * @return
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * 创建StatementHandler实例
	 * @param sql
	 * @param parameters
	 * @param autoIncrementID 是否需要返回自增的主键值, 适用于insert语句, 传入null, 则表示不需要
	 * @return
	 */
	public StatementHandler createStatementHandler(String sql, List<Object> parameters, AutoIncrementID autoIncrementID) {
		try {
			if(parameters==null || parameters.isEmpty()) {
				logger.debug("没有参数, 创建StatementHandlerImpl实例");
				return new StatementHandlerImpl(connection, sql, autoIncrementID);
			}else {
				logger.debug("有参数, 创建PreparedStatementHandler实例");
				return new PreparedStatementHandlerImpl(connection, sql, autoIncrementID);
			}
		} catch (SQLException e) {
			throw new ConnectionException("创建"+StatementHandler.class.getName()+"实例时出现异常", e);
		}
	}

	/**
	 * 提交, 遇到异常时会记录日志, 并自动回滚
	 */
	public void commit() {
		if(isBeginTransaction) {
			try {
				logger.debug("commit");
				connection.commit();
			} catch (SQLException e) {
				logger.error("commit 时出现异常, 自动进行rollback, 异常信息为: {}", ExceptionUtil.getStackTrace(e));
				rollback();
			}
		}
	}

	/**
	 * 回滚, 遇到异常时会记录日志
	 */
	public void rollback() {
		if(isBeginTransaction) {
			try {
				logger.debug("rollback");
				connection.rollback();
			} catch (SQLException e) {
				logger.error("rollback 时出现异常: {}", ExceptionUtil.getStackTrace(e));
			} 
		}
	}
	
	/**
	 * 关闭, 遇到异常时会记录日志
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error("close connection 时出现异常: {}", ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 * 是否开启事物
	 * @return
	 */
	public boolean isBeginTransaction() {
		return isBeginTransaction;
	}
	
	/**
	 * 开启事物
	 */
	public void beginTransaction() {
		try {
			setIsBeginTransaction(true);
		} catch (SQLException e) {
			throw new ConnectionException("开启事物时出现异常", e);
		}
	}
	
	/**
	 * 更新事物的隔离级别
	 * @param transactionIsolationLevel 传入null时会使用之前的隔离级别
	 */
	public void updateTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
		if(!isBeginTransaction || transactionIsolationLevel == null || transactionIsolationLevel == TransactionIsolationLevel.DEFAULT || this.transactionIsolationLevel == transactionIsolationLevel) 
			return;
		
		try {
			this.transactionIsolationLevel = transactionIsolationLevel;
			connection.setTransactionIsolation(transactionIsolationLevel.getLevel());
		} catch (SQLException e) {
			throw new ConnectionException("更新事物的隔离级别时出现异常", e);
		}
	}
}
