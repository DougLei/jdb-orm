package com.douglei.orm.sessions.session.sql;

import java.util.List;

import com.douglei.orm.core.sql.SqlStatement;
import com.douglei.orm.sessions.session.execution.ExecutionHolder;

/**
 * 
 * @author DougLei
 */
public class ExecuteSql {
	private SqlStatement sql;
	private List<Object> parameters;
	
	public ExecuteSql(ExecutionHolder executionHolder) {
		this.sql = new SqlStatement(executionHolder.getCurrentSql());
		this.parameters = executionHolder.getCurrentParameters();
	}
	
	public String getWithClause() {
		return sql.getWithClause();
	}
	public String getSql() {
		return sql.getSql();
	}
	public List<Object> getParameters(){
		return parameters;
	}
}
