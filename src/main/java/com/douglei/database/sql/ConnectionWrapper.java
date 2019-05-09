package com.douglei.database.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.TransactionIsolationLevel;
import com.douglei.database.sql.statement.StatementHandler;
import com.douglei.database.sql.statement.impl.PreparedStatementHandlerImpl;
import com.douglei.database.sql.statement.impl.StatementHandlerImpl;

/**
 * java.sql.Connection包装类
 * @author DougLei
 */
public class ConnectionWrapper {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionWrapper.class);
	private boolean beginTransaction;
	private TransactionIsolationLevel transactionIsolationLevel;
	private boolean finishTransaction;// 事物是否结束
	private Connection connection;
	
	public ConnectionWrapper(DataSource dataSource, boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		try {
			logger.debug("实例化{}", getClass());
			
			this.beginTransaction = beginTransaction;
			this.transactionIsolationLevel = transactionIsolationLevel;
			this.connection = dataSource.getConnection();
			
			processIsBeginTransaction();
			processTransactionIsolationLevel();
		} catch (SQLException e) {
			throw new RuntimeException("从数据源["+dataSource.getClass()+"]获取Connection时出现异常", e);
		}
	}
	
	// 处理是否开启事物
	private void processIsBeginTransaction() throws SQLException {
		boolean isAutoCommit = connection.getAutoCommit();
		if(logger.isDebugEnabled()) {
			logger.debug("开始处理是否开启事物");
			logger.debug("jdbc默认的事物autoCommit值为 {}", isAutoCommit);
			logger.debug("获取connection实例时，要求是否开启事物的值为 {}", beginTransaction);
		}
		if(beginTransaction) {
			if(isAutoCommit) {
				connection.setAutoCommit(false);
			}
		}else {
			if(!isAutoCommit) {
				connection.setAutoCommit(true);
			}
			finishTransaction = true;// 因为不开启事物, 则事物标识为结束
		}
		if(logger.isDebugEnabled()) {
			logger.debug("最终设置jdbc事物autoCommit值为 {}", connection.getAutoCommit());
		}
	}
	
	// 处理事物的隔离级别
	private void processTransactionIsolationLevel() throws SQLException {
		if(logger.isDebugEnabled()) {
			logger.debug("开始处理事物的隔离级别");
		}
		if(transactionIsolationLevel != null) {
			connection.setTransactionIsolation(transactionIsolationLevel.getLevel());
		}
		if(logger.isDebugEnabled()) {
			logger.debug("最终设置jdbc事物隔离级别为 {}", connection.getTransactionIsolation());
		}
	}

	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * 创建StatementHandler实例
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public StatementHandler createStatementHandler(String sql, List<Object> parameters) {
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("创建StatementHandler实例");
				logger.debug("sql语句为: {}", sql);
			}
			if(parameters==null || parameters.size()==0) {
				logger.debug("没有参数, 创建StatementHandlerImpl实例");
				return new StatementHandlerImpl(connection.createStatement(), sql);
			}else {
				if(logger.isDebugEnabled()) {
					logger.debug("有参数, 创建PreparedStatementHandler实例");
					logger.debug("参数为: {}", parameters.toString());
				}
				return new PreparedStatementHandlerImpl(connection.prepareStatement(sql));
			}
		} catch (SQLException e) {
			throw new RuntimeException("创建StatementHander实例时出现异常", e);
		}
	}

	public void commit() {
		if(beginTransaction) {
			logger.debug("commit");
			finishTransaction = true;
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new RuntimeException("commit 时出现异常", e);
			}
			return;
		}
		logger.debug("当前连接没有开启事物, commit无效");
	}

	public void rollback() {
		if(beginTransaction) {
			logger.debug("rollback");
			finishTransaction = true;
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new RuntimeException("rollback 时出现异常", e);
			}
			return;
		}
		logger.debug("当前连接没有开启事物, rollback无效");
	}
	
	/**
	 * 是否结束事物
	 * @return
	 */
	public boolean isFinishTransaction() {
		return finishTransaction;
	}
}
