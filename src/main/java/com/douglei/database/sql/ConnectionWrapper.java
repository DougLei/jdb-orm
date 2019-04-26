package com.douglei.database.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.sql.statement.StatementHandler;
import com.douglei.database.sql.statement.impl.PreparedStatementHandlerImpl;
import com.douglei.database.sql.statement.impl.StatementHandlerImpl;
import com.douglei.utils.ExceptionUtil;

/**
 * java.sql.Connection包装类
 * @author DougLei
 */
public class ConnectionWrapper {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionWrapper.class);
	private boolean beginTransaction;
	private boolean finishTransaction;// 结束事物
	private Connection connection;
	
	public ConnectionWrapper(DataSource dataSource, boolean beginTransaction) {
		try {
			logger.debug("实例化{}", getClass());
			this.beginTransaction = beginTransaction;
			this.connection = dataSource.getConnection();
			
			boolean isAutoCommit = connection.getAutoCommit();
			logger.debug("jdbc默认的事物autoCommit值为 {}", isAutoCommit);
			logger.debug("获取connection实例时，要求是否开启事物的值为 {}", beginTransaction);
			if(beginTransaction) {
				if(isAutoCommit) {
					connection.setAutoCommit(false);
				}
			}else {
				if(!isAutoCommit) {
					connection.setAutoCommit(true);
				}
				finishTransaction = true;
			}
			if(logger.isDebugEnabled()) {
				logger.debug("最终设置jdbc事物autoCommit值为 {}", connection.getAutoCommit());
			}
		} catch (SQLException e) {
			logger.error("从数据源[]获取Connection实例时出现异常: {}", dataSource.getClass(), ExceptionUtil.getExceptionDetailMessage(e));
			throw new RuntimeException("从数据源["+dataSource.getClass()+"]获取Connection时出现异常", e);
		}
	}

	/**
	 * 创建StatementHandler实例
	 * @param sql
	 * @param noParameter
	 * @return
	 */
	public StatementHandler createStatementHandler(String sql, boolean noParameter) {
		try {
			logger.debug("创建StatementHandler实例");
			if(noParameter) {
				logger.debug("没有参数, 创建StatementHandlerImpl实例");
				return new StatementHandlerImpl(connection.createStatement(), sql);
			}else {
				logger.debug("有参数, 创建PreparedStatementHandler实例");
				return new PreparedStatementHandlerImpl(connection.prepareStatement(sql));
			}
		} catch (SQLException e) {
			logger.debug("创建StatementHander实例时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
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
				logger.debug("commit 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
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
				logger.debug("rollback 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
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
