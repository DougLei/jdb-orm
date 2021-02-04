package com.douglei.orm.configuration.environment.mapping;

import com.douglei.orm.configuration.OrmException;

/**
 * 映射配置
 * @author DougLei
 */
public class MappingConfiguration {
	private boolean enableProperty; 
	private boolean enableTable; 
	private boolean enableSql;
	private boolean enableProcedure;
	private boolean enableView; 
	private boolean enableQuerySql;
	
	private SqlMappingConfiguration sqlMappingConfiguration; // 内置的sql映射配置

	/**
	 * 
	 * @param enableProperty 是否启用映射属性
	 * @param enableTable 是否启用表映射
	 * @param enableSql 是否启用sql映射
	 * @param enableProcedure 是否启用存储过程映射
	 * @param enableView 是否启用视图映射
	 * @param enableQuerySql 是否启用query-sql映射
	 */
	public MappingConfiguration(boolean enableProperty, boolean enableTable, boolean enableSql, boolean enableProcedure, boolean enableView, boolean enableQuerySql) {
		this.enableProperty = enableProperty;
		this.enableTable = enableTable;
		this.enableSql = enableSql;
		this.enableProcedure = enableProcedure;
		this.enableView = enableView;
		this.enableQuerySql = enableQuerySql;
	}
	
	/**
	 * 设置内置的sql映射配置
	 * @param sqlMappingConfiguration
	 */
	public void setSqlMappingConfiguration(SqlMappingConfiguration sqlMappingConfiguration) {
		this.sqlMappingConfiguration = sqlMappingConfiguration;
	}

	/**
	 * 是否启用映射属性
	 * @return
	 */
	public boolean isEnableProperty() {
		return enableProperty;
	}
	/**
	 * 是否启用表映射
	 * @return
	 */
	public boolean isEnableTable() {
		return enableTable;
	}
	/**
	 * 是否启用sql映射
	 * @return
	 */
	public boolean isEnableSql() {
		return enableSql;
	}
	/**
	 * 是否启用存储过程映射
	 * @return
	 */
	public boolean isEnableProcedure() {
		return enableProcedure;
	}
	/**
	 * 是否启用视图映射
	 * @return
	 */
	public boolean isEnableView() {
		return enableView;
	}
	/**
	 * 是否启用query-sql映射
	 * @return
	 */
	public boolean isEnableQuerySql() {
		return enableQuerySql;
	}
	/**
	 * 获取内置的sql映射配置
	 * @return
	 */
	public SqlMappingConfiguration getSqlMappingConfiguration() {
		if(enableSql)
			throw new OrmException("未启用sql映射, 无法获取内置的sql映射配置");
		return sqlMappingConfiguration;
	}

	@Override
	public String toString() {
		return "MappingConfiguration [enableProperty=" + enableProperty + ", enableTable=" + enableTable
				+ ", enableSql=" + enableSql + ", enableProcedure=" + enableProcedure + ", enableView=" + enableView
				+ ", enableQuerySql=" + enableQuerySql + ", sqlMappingConfiguration=" + sqlMappingConfiguration + "]";
	}
}
