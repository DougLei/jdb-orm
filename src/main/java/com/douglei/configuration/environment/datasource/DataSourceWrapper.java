package com.douglei.configuration.environment.datasource;

import javax.sql.DataSource;

import com.douglei.configuration.SelfProcessing;
import com.douglei.core.dialect.TransactionIsolationLevel;
import com.douglei.core.sql.ConnectionWrapper;

/**
 * 
 * @author DougLei
 */
public interface DataSourceWrapper extends SelfProcessing{

	/**
	 * 获取数据源
	 * @return
	 */
	abstract DataSource getDataSource();
	
	/**
	 * 获取连接
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	abstract ConnectionWrapper getConnection(boolean beginTransaction);
	
	/**
	 * 获取连接
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果为空, 则使用jdbc默认的隔离级别
	 * @return
	 */
	abstract ConnectionWrapper getConnection(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
}
