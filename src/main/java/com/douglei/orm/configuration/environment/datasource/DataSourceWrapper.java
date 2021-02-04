package com.douglei.orm.configuration.environment.datasource;

import javax.sql.DataSource;

import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class DataSourceWrapper {
	private DataSource dataSource;
	private String closeMethodName;
	
	public DataSourceWrapper(DataSource dataSource, String closeMethodName) {
		this.dataSource = dataSource;
		this.closeMethodName = closeMethodName;
	}
	
	/**
	 * 获取连接
	 * @param isBeginTransaction 是否开启事物
	 * @return
	 */
	public ConnectionWrapper getConnection(boolean isBeginTransaction) {
		return getConnection(isBeginTransaction, null);
	}
	
	/**
	 * 获取连接
	 * @param isBeginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果为空, 则使用jdbc默认的隔离级别
	 * @return
	 */
	public ConnectionWrapper getConnection(boolean isBeginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return new ConnectionWrapper(dataSource, isBeginTransaction, transactionIsolationLevel);
	}
	
	/**
	 * 获取数据源实例
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 关闭数据源
	 * @throws Exception 
	 */
	public void close() throws Exception {
		if(dataSource != null && StringUtil.unEmpty(closeMethodName)) 
			dataSource.getClass().getMethod(closeMethodName).invoke(dataSource);
	}	
}
