package com.douglei.orm.configuration.environment.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.sql.statement.AutoIncrementID;
import com.douglei.orm.sql.statement.StatementHandler;
import com.douglei.orm.sql.statement.impl.PreparedStatementHandlerImpl;
import com.douglei.orm.sql.statement.impl.StatementHandlerImpl;
import com.douglei.tools.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public class ConnectionEntity {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionEntity.class);
	private DataSource dataSource;
	private Connection connection;
	private byte state; // 连接状态: 0未开启, 1未初始化(过渡状态), 2开启, 3关闭
	private boolean isBeginTransaction; // 是否开启事物
	private int defaultTransactionIsolationLevel; // jdbc驱动中默认的事物隔离级别
	private int transactionIsolationLevel; // 事物隔离级别
	
	public ConnectionEntity(DataSource dataSource, boolean isBeginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		this.dataSource = dataSource;
		this.isBeginTransaction = isBeginTransaction;
		this.transactionIsolationLevel = transactionIsolationLevel.getLevel();
		logger.debug("创建数据库连接ConnectionEntity实例");
	}
	
	/**
	 * 获取数据库原生连接实例
	 * @return
	 */
	public Connection getConnection() {
		if(state == 0) {
			try {
				logger.debug("获取数据库连接Connection实例");
				this.connection = dataSource.getConnection();
				this.state++; 
				
				// 处理事物
				setIsBeginTransaction(isBeginTransaction); 
				
				// 处理事物隔离级别
				this.defaultTransactionIsolationLevel = connection.getTransactionIsolation();
				if(transactionIsolationLevel == -1)
					transactionIsolationLevel = defaultTransactionIsolationLevel;
				if(transactionIsolationLevel != defaultTransactionIsolationLevel) 
					connection.setTransactionIsolation(transactionIsolationLevel); 
				
				this.state++; 
			} catch (Exception e) {
				throw new ConnectionException("获取Connection时出现异常", e);
			}
		}
		return connection;
	}
	
	// 设置是否开启事务
	private void setIsBeginTransaction(boolean isBeginTransaction) throws SQLException {
		switch(state) {
			case 0:
				this.isBeginTransaction = isBeginTransaction; // 连接未开启时, 是否开启事务的状态可随意切换
				break;
			case 2:
				if(this.isBeginTransaction == isBeginTransaction) // 连接开启后, 如果是否开启事务的状态相同, 则不进行切换
					return;
				this.isBeginTransaction = isBeginTransaction; 
			case 1:
				if(isBeginTransaction) { // 设置是否开启事物
					if(connection.getAutoCommit()) 
						connection.setAutoCommit(false);
				}else {
					if(!connection.getAutoCommit()) 
						connection.setAutoCommit(true);
				}
				break;
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
	 * @param transactionIsolationLevel
	 */
	public void updateTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
		if(state == 0) { // 连接未开启时, 事物的隔离级别可随意切换
			this.transactionIsolationLevel = transactionIsolationLevel.getLevel();
			return;
		}
		
		if(state == 2) { // 连接开启后, 设置事物隔离级别
			try { 
				int targetLevel = transactionIsolationLevel.getLevel();
				if(targetLevel == -1)
					targetLevel = defaultTransactionIsolationLevel;
				
				if(this.transactionIsolationLevel != targetLevel) {
					this.transactionIsolationLevel = targetLevel;
					connection.setTransactionIsolation(targetLevel); 
				}
			} catch (SQLException e) {
				throw new ConnectionException("设置事物的隔离级别时出现异常", e);
			}
		}
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
			if(parameters==null || parameters.isEmpty()) 
				return new StatementHandlerImpl(getConnection(), sql, autoIncrementID);
			return new PreparedStatementHandlerImpl(getConnection(), sql, autoIncrementID);
		} catch (SQLException e) {
			throw new ConnectionException("创建"+StatementHandler.class.getName()+"实例时出现异常", e);
		}
	}

	/**
	 * 提交, 遇到异常时会记录日志, 并自动回滚
	 */
	public void commit() {
		if(state == 2 && isBeginTransaction) {
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
		if(state == 2 && isBeginTransaction) {
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
		if(state == 2) {
			try {
				connection.close();
				state++;
			} catch (SQLException e) {
				logger.error("close connection 时出现异常: {}", ExceptionUtil.getStackTrace(e));
			}
		}
	}
}