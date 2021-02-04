package com.douglei.orm.configuration;

import javax.sql.DataSource;

/**
 * 
 * @author DougLei
 */
public class ExternalDataSource {
	private DataSource dataSource; // 数据源实例
	private String closeMethodName; // 数据源关闭的方法名

	/**
	 * 
	 * @param dataSource 数据源实例
	 */
	public ExternalDataSource(DataSource dataSource) {
		this(dataSource, null);
	}
	
	/**
	 * 
	 * @param dataSource 数据源实例
	 * @param closeMethodName 数据源关闭的方法名
	 */
	public ExternalDataSource(DataSource dataSource, String closeMethodName) {
		this.dataSource = dataSource;
		this.closeMethodName = closeMethodName;
	}
	
	/**
	 * 获取数据源实例
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	/**
	 * 获取数据源关闭的方法名
	 * @return
	 */
	public String getCloseMethodName() {
		return closeMethodName;
	}
}
