package com.douglei.sessions.session.sql.impl.persistent.execution;

import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.sql.SqlMetadata;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;

/**
 * 
 * @author DougLei
 */
public class SqlExecutionHolder implements ExecutionHolder{

	public SqlExecutionHolder(SqlMetadata sqlMetadata, Map<String, Object> sqlParameterMap) {
		// TODO Auto-generated constructor stub
	}
	

	private int executeSqlIndex; // 从0开始
	private List<String> executeSqls;
	private List<List<Object>> parameters;
	
	@Override
	public int executeSqlCount() {
		return executeSqls.size();
	}

	@Override
	public boolean next() {
		executeSqlIndex++;
		return executeSqlIndex < executeSqls.size();
	}

	@Override
	public String getCurrentSql() {
		return executeSqls.get(executeSqlIndex);
	}

	@Override
	public List<Object> getCurrentParameters() {
		return parameters.get(executeSqlIndex);
	}

}
