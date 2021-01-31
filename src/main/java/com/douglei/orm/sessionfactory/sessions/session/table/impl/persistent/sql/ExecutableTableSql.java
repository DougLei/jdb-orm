package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.session.IExecutableSql;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class ExecutableTableSql implements IExecutableSql {
	protected TableMetadata tableMetadata;
	protected Map<String, Object> objectMap;
	
	protected ExecutableTableSql() {}
	protected ExecutableTableSql(TableMetadata tableMetadata, Map<String, Object> objectMap) {
		setBaseInfo(tableMetadata, objectMap);
		initial();
	}
	
	/**
	 * 设置基础信息
	 * @param tableMetadata
	 * @param objectMap
	 */
	protected void setBaseInfo(TableMetadata tableMetadata, Map<String, Object> objectMap) {
		this.tableMetadata = tableMetadata;
		this.objectMap = objectMap;
	}
	
	/**
	 * 初始化
	 */
	protected abstract void initial();
	
	protected String sql;
	protected List<Object> parameters;
	
	@Override
	public String getCurrentSql() {
		return sql;
	}

	@Override
	public List<Object> getCurrentParameterValues() {
		return parameters;
	}
	
	@Override
	public String toString() {
		return "TableExecuteHandler [sql=" + (StringUtil.isEmpty(sql)?"sql is null":sql) + ", parameters=" + (parameters==null || parameters.isEmpty()?"parameters is null":parameters.toString()) + "]";
	}
}
