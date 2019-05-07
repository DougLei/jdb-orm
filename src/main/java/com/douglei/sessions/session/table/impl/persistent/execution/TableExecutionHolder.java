package com.douglei.sessions.session.table.impl.persistent.execution;

import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.sql.statement.impl.Parameter;
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
		initialInstance();
	}

	/**
	 * 初始化实例
	 */
	protected abstract void initialInstance();
	
	
	protected String sql;
	protected List<Parameter> parameters;
	
	@Override
	public String getSql() {
		return sql;
	}

	@Override
	public List<? extends Object> getParameters() {
		return parameters;
	}
	
	@Override
	public String toString() {
		return "\n" + 
				(StringUtil.isEmpty(sql)?"sql is null":sql) + 
				" \n------------------------------------------------------------\n " + 
				((parameters==null || parameters.size()==0)?"parameters is null":parameters.toString());
	}
}
