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

	@Override
	public String getSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
