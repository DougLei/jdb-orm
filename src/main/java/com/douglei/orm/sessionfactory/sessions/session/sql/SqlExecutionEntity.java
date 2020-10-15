package com.douglei.orm.sessionfactory.sessions.session.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.SqlExecuteHandler;
import com.douglei.orm.sql.SqlStatement;

/**
 * 
 * @author DougLei
 */
public class SqlExecutionEntity {
	private SqlExecuteHandler sqlExecutionHandler;
	private String name;
	private ContentType type;
	private SqlStatement sql;
	private List<Object> parameters;
	
	public SqlExecutionEntity(SqlExecuteHandler sqlExecutionHandler) {
		this.sqlExecutionHandler = sqlExecutionHandler;
		this.name = sqlExecutionHandler.getCurrentName();
		this.type = sqlExecutionHandler.getCurrentType();
		this.sql = new SqlStatement(EnvironmentContext.getDialect().getSqlStatementHandler(), sqlExecutionHandler.getCurrentSql());
		this.parameters = sqlExecutionHandler.getCurrentParameters();
	}
	
	/**
	 * 获取当前执行sql的名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取当前执行sql的类型
	 * @return
	 */
	public ContentType getType() {
		return type;
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
	 * 获取order by子句, 如果是select类型的sql
	 * @return
	 */
	public String getOrderByClause() {
		return sql.getOrderByClause();
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
	public int executeSqlCount() {
		return sqlExecutionHandler.executeSqlCount();
	}
	
	/**
	 * 是否还有下一个要执行的sql, 如果有, 则移动到下一个sql
	 * @return
	 */
	public boolean next() {
		if(this.sqlExecutionHandler.next()) {
			this.name = sqlExecutionHandler.getCurrentName();
			this.type = sqlExecutionHandler.getCurrentType();
			this.sql.reset(sqlExecutionHandler.getCurrentSql());
			this.parameters = sqlExecutionHandler.getCurrentParameters();
			return true;
		}
		return false;
	}
}
