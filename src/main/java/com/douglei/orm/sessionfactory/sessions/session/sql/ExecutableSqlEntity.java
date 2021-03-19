package com.douglei.orm.sessionfactory.sessions.session.sql;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.AutoIncrementIDMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSqlList;

/**
 * 
 * @author DougLei
 */
public class ExecutableSqlEntity {
	private ExecutableSqlList list;
	
	public ExecutableSqlEntity(ExecutableSqlList list) {
		this.list = list;
	}
	
	/**
	 * 获取可执行的sql数量
	 * @return
	 */
	public int executableSqlCount() {
		return list.executableSqlCount();
	}
	
	/**
	 * 是否还有下一个要执行的sql, 如果有, 则移动到下一个sql
	 * @return
	 */
	public boolean next() {
		return list.next();
	}
	
	/**
	 * 获取sql的名称
	 * @return
	 */
	public String getName() {
		return list.getCurrentName();
	}
	
	/**
	 * 获取当前执行sql的类型
	 * @return
	 */
	public ContentType getType() {
		return list.getCurrentType();
	}
	
	/**
	 * 获取sql语句
	 * @return
	 */
	public String getSql() {
		return list.getCurrentSql();
	}
	
	/**
	 * 获取sql参数集合
	 * @return 可能为null
	 */
	public List<ParameterNode> getParameters(){
		return list.getCurrentParameters();
	}
	
	/**
	 * 获取执行sql语句时的参数值集合
	 * @return 可能为null
	 */
	public List<Object> getParameterValues(){
		return list.getCurrentParameterValues();
	}
	
	/**
	 * 获取sql自增主键值(配置)
	 * @return
	 */
	public AutoIncrementIDMetadata getCurrentAutoIncrementID() {
		return list.getCurrentAutoIncrementID();
	}
}
