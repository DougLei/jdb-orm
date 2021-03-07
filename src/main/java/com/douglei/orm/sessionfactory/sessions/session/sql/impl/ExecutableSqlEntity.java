package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.AutoIncrementIDMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;

/**
 * 
 * @author DougLei
 */
public class ExecutableSqlEntity {
	private ExecutableSqlHolder executableSqlHolder;
	private String name;
	private ContentType type;
	private String sql;
	private List<ParameterNode> parameters;
	private List<Object> parameterValues;
	private AutoIncrementIDMetadata autoIncrementID;
	
	public ExecutableSqlEntity(ExecutableSqlHolder executableSqlHolder) {
		this.executableSqlHolder = executableSqlHolder;
		this.name = executableSqlHolder.getCurrentName();
		this.type = executableSqlHolder.getCurrentType();
		this.sql = executableSqlHolder.getCurrentSql();
		this.parameters = executableSqlHolder.getCurrentParameters();
		this.parameterValues = executableSqlHolder.getCurrentParameterValues();
		this.autoIncrementID = executableSqlHolder.getCurrentAutoIncrementID();
	}
	
	/**
	 * 获取可执行的sql数量
	 * @return
	 */
	public int executableSqlCount() {
		return executableSqlHolder.executableSqlCount();
	}
	
	/**
	 * 是否还有下一个要执行的sql, 如果有, 则移动到下一个sql
	 * @return
	 */
	public boolean next() {
		if(this.executableSqlHolder.next()) {
			this.name = executableSqlHolder.getCurrentName();
			this.type = executableSqlHolder.getCurrentType();
			this.sql = executableSqlHolder.getCurrentSql();
			this.parameters = executableSqlHolder.getCurrentParameters();
			this.parameterValues = executableSqlHolder.getCurrentParameterValues();
			this.autoIncrementID = executableSqlHolder.getCurrentAutoIncrementID();
			return true;
		}
		return false;
	}
	
	/**
	 * 获取sql的名称
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
	 * 获取sql语句
	 * @return
	 */
	public String getSql() {
		return sql;
	}
	
	/**
	 * 获取sql参数集合
	 * @return 可能为null
	 */
	public List<ParameterNode> getParameters(){
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
	 * 获取sql自增主键值(配置)
	 * @return
	 */
	public AutoIncrementIDMetadata getCurrentAutoIncrementID() {
		return autoIncrementID;
	}
}
