package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.List;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
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
	private List<SqlParameterMetadata> sqlParameters;
	
	public SqlExecutionEntity(SqlExecuteHandler sqlExecutionHandler) {
		this.sqlExecutionHandler = sqlExecutionHandler;
		this.name = sqlExecutionHandler.getCurrentName();
		this.type = sqlExecutionHandler.getCurrentType();
		this.sql = new SqlStatement(EnvironmentContext.getDialect().getSqlStatementHandler(), sqlExecutionHandler.getCurrentSql());
		this.parameters = sqlExecutionHandler.getCurrentParameters();
		this.sqlParameters = sqlExecutionHandler.getCurrentSqlParameters();
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
	 * 获取执行sql语句对应的参数值集合
	 * @return 可能为null
	 */
	public List<Object> getParameters(){
		return parameters;
	}
	
	/**
	 * 获取sql参数集合
	 * @return 可能为null
	 */
	public List<SqlParameterMetadata> getSqlParameters(){
		return sqlParameters;
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
			this.sqlParameters = sqlExecutionHandler.getCurrentSqlParameters();
			return true;
		}
		return false;
	}
}
