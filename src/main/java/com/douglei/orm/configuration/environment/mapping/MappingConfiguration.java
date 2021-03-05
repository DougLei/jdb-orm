package com.douglei.orm.configuration.environment.mapping;

/**
 * 映射配置
 * @author DougLei
 */
public class MappingConfiguration {
	private SqlMappingConfiguration sqlMappingConfiguration; // 内置的sql映射配置

	/**
	 * 设置内置的sql映射配置
	 * @param sqlMappingConfiguration
	 */
	public void setSqlMappingConfiguration(SqlMappingConfiguration sqlMappingConfiguration) {
		this.sqlMappingConfiguration = sqlMappingConfiguration;
	}

	/**
	/**
	 * 获取内置的sql映射配置
	 * @return
	 */
	public SqlMappingConfiguration getSqlMappingConfiguration() {
		return sqlMappingConfiguration;
	}

	@Override
	public String toString() {
		return "MappingConfiguration [sqlMappingConfiguration=" + sqlMappingConfiguration + "]";
	}
}
