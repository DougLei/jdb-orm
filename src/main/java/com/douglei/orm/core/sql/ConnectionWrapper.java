package com.douglei.orm.core.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.sql.statement.StatementHandler;
import com.douglei.orm.core.sql.statement.impl.PreparedStatementHandlerImpl;
import com.douglei.orm.core.sql.statement.impl.StatementHandlerImpl;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * java.sql.Connection包装类
 * @author DougLei
 */
public class ConnectionWrapper {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionWrapper.class);
	private boolean beginTransaction;
	private TransactionIsolationLevel transactionIsolationLevel;
	private boolean finishTransaction;// 事物是否结束
	private boolean isClosed;// 连接是否关闭
	private Connection connection;
	
	public ConnectionWrapper(DataSource dataSource, boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		try {
			logger.debug("实例化{}", getClass().getName());
			
			this.beginTransaction = beginTransaction;
			this.transactionIsolationLevel = transactionIsolationLevel;
			this.connection = dataSource.getConnection();
			
			processIsBeginTransaction();
			processTransactionIsolationLevel();
		} catch (SQLException e) {
			throw new ConnectionWrapperException("从数据源["+dataSource.getClass().getName()+"]获取Connection时出现异常", e);
		}
	}
	
	// 处理是否开启事物
	private void processIsBeginTransaction() throws SQLException {
		if(beginTransaction) {
			if(connection.getAutoCommit()) {
				connection.setAutoCommit(false);
			}
		}else {
			if(!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
			finishTransaction = true;// 因为不开启事物, 则事物标识为结束
		}
	}
	
	// 处理事物的隔离级别
	private void processTransactionIsolationLevel() throws SQLException {
		if(transactionIsolationLevel != null && transactionIsolationLevel != TransactionIsolationLevel.DEFAULT) {
			connection.setTransactionIsolation(transactionIsolationLevel.getLevel());
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
			throw new ConnectionWrapperException("创建"+StatementHandler.class.getName()+"实例时出现异常", e);
		}
	}

	public void commit() {
		if(beginTransaction && !finishTransaction) {
			logger.debug("commit");
			try {
				connection.commit();
				finishTransaction = true;
			} catch (SQLException e) {
				logger.error("commit 时出现异常, 进行rollback, 异常信息为: {}", ExceptionUtil.getExceptionDetailMessage(e));
				rollback();
			} finally {
				close();
			}
		} else {
			logger.debug("当前连接没有开启事物, commit无效");
		}
	}

	public void rollback() {
		if(beginTransaction && !finishTransaction) {
			logger.debug("rollback");
			try {
				connection.rollback();
				finishTransaction = true;
			} catch (SQLException e) {
				logger.error("rollback 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				throw new ConnectionWrapperException("rollback 时出现异常", e);
			} finally {
				close();
			}
		} else {
			logger.debug("当前连接没有开启事物, rollback无效");
		}
	}
	
	public void close() {
		if(!isClosed) {
			try {
				connection.close();
				isClosed = true;
			} catch (SQLException e) {
				logger.error("close connection 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				throw new ConnectionWrapperException("close connection 时出现异常", e);
			}
		}
	}
	
	/**
	 * 是否结束事物
	 * @return
	 */
	public boolean isFinishTransaction() {
		return finishTransaction;
	}
	
	/**
	 * 连接是否关闭
	 * @return
	 */
	public boolean isClosed() {
		return isClosed;
	}
	
	/**
	 * 是否开启事物
	 * @return
	 */
	public boolean isBeginTransaction() {
		return beginTransaction;
	}
	
	/**
	 * 开启事物
	 */
	public void beginTransaction() {
		if(isClosed) {
			throw new ConnectionWrapperException("连接已经关闭, 无法开启事物");
		}
		
		this.beginTransaction = true;
		try {
			processIsBeginTransaction();
			this.finishTransaction= false;
		} catch (SQLException e) {
			throw new ConnectionWrapperException("开启事物时出现异常", e);
		}
	}

	/**
	 * 设置事物的隔离级别
	 * @param transactionIsolationLevel
	 */
	public void setTransactionIsolationLevel(TransactionIsolationLevel transactionIsolationLevel) {
		if(isClosed) {
			throw new ConnectionWrapperException("连接已经关闭, 无法设置事物的隔离级别");
		}
		if(transactionIsolationLevel == null || transactionIsolationLevel == TransactionIsolationLevel.DEFAULT || this.transactionIsolationLevel == transactionIsolationLevel) {
			return;
		}
		
		this.transactionIsolationLevel = transactionIsolationLevel;
		try {
			connection.setTransactionIsolation(transactionIsolationLevel.getLevel());
		} catch (SQLException e) {
			throw new ConnectionWrapperException("设置事物的隔离级别时出现异常", e);
		}
	}
}
