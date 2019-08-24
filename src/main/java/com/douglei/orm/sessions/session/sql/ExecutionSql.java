package com.douglei.orm.sessions.session.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.sql.SqlStatement;
import com.douglei.orm.sessions.session.execution.ExecutionHolder;

/**
 * 
 * @author DougLei
 */
public class ExecutionSql {
	private ExecutionHolder executionHolder;
	private SqlStatement sql;
	private List<Object> parameters;
	
	public ExecutionSql(ExecutionHolder executionHolder) {
		this.executionHolder = executionHolder;
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
		if(parameters == null) {
			parameters = new ArrayList<Object>();
		}
		return parameters;
	}
	
	public short executeSqlCount() {
		return executionHolder.executeSqlCount();
	}
	
	public boolean next() {
		boolean next = this.executionHolder.next();
		if(next) {
			this.sql.setSql(executionHolder.getCurrentSql());
			this.parameters = executionHolder.getCurrentParameters();
		}
		return next;
	}
}
