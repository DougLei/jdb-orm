package com.douglei.orm.environment.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class DataSourceWrapper {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceWrapper.class);
	
	private DataSource dataSource;
	private String closeMethodName;
	
	public DataSourceWrapper(DataSource dataSource, String closeMethodName, Map<String, String> propertyMap) {
		this.dataSource = dataSource;
		this.closeMethodName = closeMethodName;
		
		if(propertyMap != null) {
			logger.debug("开始给数据源 {} 的属性设置值", dataSource.getClass().getName());
			IntrospectorUtil.setProperyValues(dataSource, propertyMap);
			propertyMap.clear();
			logger.debug("结束给数据源 {} 的属性设置值", dataSource.getClass());
		}
	}
	
	/**
	 * 关闭
	 * @throws Exception 
	 */
	public void close() throws Exception {
		if(dataSource != null && StringUtil.notEmpty(closeMethodName)) 
			dataSource.getClass().getMethod(closeMethodName).invoke(dataSource);
	}	
	
	/**
	 * 获取数据源
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 获取连接
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	public ConnectionWrapper getConnection(boolean beginTransaction) {
		return getConnection(beginTransaction, null);
	}
	
	/**
	 * 获取连接
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果为空, 则使用jdbc默认的隔离级别
	 * @return
	 */
	public ConnectionWrapper getConnection(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return new ConnectionWrapper(dataSource, beginTransaction, transactionIsolationLevel);
	}
}
