package com.douglei.orm.configuration;

import javax.sql.DataSource;

/**
 * 外部的数据源
 * @author DougLei
 */
public class ExternalDataSource {
	private DataSource dataSource;
	private String closeMethodName;
	
	/**
	 * 
	 * @param dataSource
	 * @param closeMethodName 可以传入null
	 */
	public ExternalDataSource(DataSource dataSource, String closeMethodName) {
		this.dataSource = dataSource;
		this.closeMethodName = closeMethodName;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public String getCloseMethodName() {
		return closeMethodName;
	}
}
