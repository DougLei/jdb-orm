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
	
	/**
	 * 获取with子句, 如果是select类型的sql
	 * @return
	 */
	public String getWithClause() {
		return sql.getWithClause();
	}
	
	/**
	 * 获取sql语句
	 * @return
	 */
	public String getSql() {
		return sql.getSql();
	}
	
	/**
	 * 获取参数list
	 * @return
	 */
	public List<Object> getParameters(){
		if(parameters == null) {
			parameters = new ArrayList<Object>();
		}
		return parameters;
	}
	
	/**
	 * 获取要执行的sql数量
	 * @return
	 */
	public short executeSqlCount() {
		return executionHandler.executeSqlCount();
	}
	
	/**
	 * 是否还有下一个要执行的sql, 如果有, 则移动到下一个sql
	 * @return
	 */
	public boolean next() {
		if(this.executionHandler.next()) {
			this.sql.resetSql(executionHandler.getCurrentSql());
			this.parameters = executionHandler.getCurrentParameters();
			return true;
		}
		return false;
	}
}
