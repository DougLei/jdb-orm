package com.douglei.orm.mapping.impl.sqlquery.metadata;

import java.util.Map;

import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public class SqlQueryMetadata extends AbstractMetadata {
	private SqlMetadata sql;
	private Map<String, ParameterMetadata> parameterMap;
	
	public SqlQueryMetadata(String name, SqlMetadata sql) {
		this.name = name;
		this.sql = sql;
	}
	public void setParameterMap(Map<String, ParameterMetadata> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public SqlMetadata getSql() {
		return sql;
	}
	public Map<String, ParameterMetadata> getParameterMap() {
		return parameterMap;
	}
}
