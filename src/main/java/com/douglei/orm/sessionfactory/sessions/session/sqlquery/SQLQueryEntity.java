package com.douglei.orm.sessionfactory.sessions.session.sqlquery;

import java.util.List;

import com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl.AbstractParameter;

/**
 * 
 * @author DougLei
 */
public class SQLQueryEntity {
	private String name;
	private List<AbstractParameter> parameters;

	/**
	 * 
	 * @param name sql-query的映射名
	 */
	public SQLQueryEntity(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @param name sql-query的映射名
	 * @param parameters (动态)参数集合
	 */
	public SQLQueryEntity(String name, List<AbstractParameter> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	/**
	 * 获取sql-query的映射名
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 获取(动态)参数集合
	 * @return
	 */
	public List<AbstractParameter> getParameters() {
		return parameters;
	}
}
