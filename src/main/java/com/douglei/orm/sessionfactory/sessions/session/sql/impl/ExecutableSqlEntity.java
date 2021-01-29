package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.util.List;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sql.SqlStatement;

/**
 * 
 * @author DougLei
 */
public class ExecutableSqlEntity {
	private ExecutableSqls executableSqls;
	private String name;
	private ContentType type;
	private SqlStatement sql;
	private List<SqlParameterMetadata> parameters;
	private List<Object> parameterValues;
	
	public ExecutableSqlEntity(ExecutableSqls executableSqls) {
		this.executableSqls = executableSqls;
		this.name = executableSqls.getCurrentName();
		this.type = executableSqls.getCurrentType();
		this.sql = new SqlStatement(EnvironmentContext.getDialect().getSqlStatementHandler(), executableSqls.getCurrentSql());
		this.parameters = executableSqls.getCurrentParameters();
		this.parameterValues = executableSqls.getCurrentParameterValues();
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
	 * 获取sql参数集合
	 * @return 可能为null
	 */
	public List<SqlParameterMetadata> getParameters(){
		return parameters;
	}
	
	/**
	 * 获取执行sql语句时的参数值集合
	 * @return 可能为null
	 */
	public List<Object> getParameterValues(){
		return parameterValues;
	}
	
	/**
	 * 获取可执行的sql数量
	 * @return
	 */
	public int executableSqlCount() {
		return executableSqls.executableSqlCount();
	}
	
	/**
	 * 是否还有下一个要执行的sql, 如果有, 则移动到下一个sql
	 * @return
	 */
	public boolean next() {
		if(this.executableSqls.next()) {
			this.name = executableSqls.getCurrentName();
			this.type = executableSqls.getCurrentType();
			this.sql.reset(executableSqls.getCurrentSql());
			this.parameters = executableSqls.getCurrentParameters();
			this.parameterValues = executableSqls.getCurrentParameterValues();
			return true;
		}
		return false;
	}
}