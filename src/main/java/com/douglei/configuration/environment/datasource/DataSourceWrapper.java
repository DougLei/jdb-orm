package com.douglei.configuration.environment.datasource;

import javax.sql.DataSource;

import com.douglei.configuration.SelfProcessing;
import com.douglei.database.sql.ConnectionWrapper;

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
}
