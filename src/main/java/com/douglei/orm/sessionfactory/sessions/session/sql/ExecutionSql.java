package com.douglei.orm.sessionfactory.sessions.session.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.sql.SqlStatement;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;

/**
 * 
 * @author DougLei
 */
public class ExecutionSql {
	private ExecuteHandler executionHandler;
	private SqlStatement sql;
	private List<Object> parameters;
	
	public ExecutionSql(ExecuteHandler executionHandler) {
		this.executionHandler = executionHandler;
		this.sql = new SqlStatement(executionHandler.getCurrentSql());
		this.parameters = executionHandler.getCurrentParameters();
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
		return executionHandler.executeSqlCount();
	}
	
	public boolean next() {
		boolean next = this.executionHandler.next();
		if(next) {
			this.sql.setSql(executionHandler.getCurrentSql());
			this.parameters = executionHandler.getCurrentParameters();
		}
		return next;
	}
}
