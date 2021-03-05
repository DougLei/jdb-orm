package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.session.IExecutableSql;

/**
 * 可执行的表SQL
 * @author DougLei
 */
public abstract class ExecutableTableSql implements IExecutableSql {
	protected TableMetadata tableMetadata; 
	protected Map<String, Object> objectMap;
	
	protected String sql; // 要执行的sql
	protected List<Object> parameters; // 执行sql需要的参数集合
	
	/**
	 * 
	 * @param tableMetadata
	 * @param objectMap
	 */
	protected ExecutableTableSql(TableMetadata tableMetadata, Map<String, Object> objectMap) {
		this.tableMetadata = tableMetadata;
		this.objectMap = objectMap;
	}

	@Override
	public String getCurrentSql() {
		return sql;
	}

	@Override
	public List<Object> getCurrentParameterValues() {
		return parameters;
	}
}
