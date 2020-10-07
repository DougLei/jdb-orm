package com.douglei.orm;

import javax.sql.DataSource;

/**
 * 外部的数据源
 * @author DougLei
 */
public class ExternalDataSource {
	private DataSource dataSource;
	private String closeMethodName;

	public ExternalDataSource(DataSource dataSource) {
		this(dataSource, null);
	}
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
