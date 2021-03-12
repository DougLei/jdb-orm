package com.douglei.orm.configuration.environment.datasource;

import javax.sql.DataSource;

import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class DataSourceEntity {
	private DataSource dataSource;
	private String closeMethodName;
	
	public DataSourceEntity(DataSource dataSource, String closeMethodName) {
		this.dataSource = dataSource;
		this.closeMethodName = closeMethodName;
	}
	
	/**
	 * 获取连接
	 * @param isBeginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果为空, 则使用jdbc默认的隔离级别
	 * @return
	 */
	public ConnectionEntity getConnection(boolean isBeginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return new ConnectionEntity(dataSource, isBeginTransaction, transactionIsolationLevel);
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
