package com.douglei.sessions.session.table.impl.persistent.execution;

import java.util.List;
import java.util.Map;

import com.douglei.core.metadata.table.TableMetadata;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class TableExecutionHolder implements ExecutionHolder{
	protected TableMetadata tableMetadata;
	protected Map<String, Object> propertyMap;
	
	public TableExecutionHolder(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		this.tableMetadata = tableMetadata;
		this.propertyMap = propertyMap;
		initializeInstance();
	}

	/**
	 * 初始化实例
	 */
	protected abstract void initializeInstance();
	
	
	protected String sql;
	protected List<Object> parameters;
	
	@Override
	public String getCurrentSql() {
		return sql;
	}

	@Override
	public List<Object> getCurrentParameters() {
		return parameters;
	}
	
	@Deprecated
	@Override
	public int executeSqlCount() {
		return 1;
	}

	@Deprecated
	@Override
	public boolean next() {
		return false;
	}
	
	@Override
	public String toString() {
		return "\n" + 
				(StringUtil.isEmpty(sql)?"sql is null":sql) + 
				" \n------------------------------------------------------------\n " + 
				((parameters==null || parameters.size()==0)?"parameters is null":parameters.toString());
	}
}
