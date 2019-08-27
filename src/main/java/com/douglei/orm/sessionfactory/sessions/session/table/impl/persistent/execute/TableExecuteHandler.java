package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute;

import java.util.List;
import java.util.Map;

import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;
import com.douglei.tools.utils.Collections;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class TableExecuteHandler implements ExecuteHandler {
	protected TableMetadata tableMetadata;
	protected Map<String, Object> propertyMap;
	
	public TableExecuteHandler(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
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
	public short executeSqlCount() {
		return 1;
	}

	@Deprecated
	@Override
	public boolean next() {
		return false;
	}
	
	@Override
	public String toString() {
		return "\n" + getClass().getName() +
			   "\n" + (StringUtil.isEmpty(sql)?"sql is null":sql) + 
			   "\n" + (Collections.isEmpty(parameters)?"parameters is null":parameters.toString()) + 
			   "\n";
	}
}
