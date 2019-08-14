package com.douglei.orm.sessions.session.sql;

import java.util.List;

import com.douglei.orm.sessions.session.execution.ExecutionHolder;

/**
 * 
 * @author DougLei
 */
public class ExecuteSql {
	private ExecutionHolder executionHolder;
	
	public ExecuteSql(ExecutionHolder executionHolder) {
		this.executionHolder = executionHolder;
	}
	
	public String getSql() {
		return executionHolder.getCurrentSql();
	}
	public List<Object> getParameters(){
		return executionHolder.getCurrentParameters();
	}
}
