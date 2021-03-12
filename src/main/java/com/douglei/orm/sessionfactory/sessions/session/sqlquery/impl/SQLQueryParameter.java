package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public class SQLQueryParameter {
	private String name;
	private List<AbstractParameter> parameters;

	
	public SQLQueryParameter(String name) {
		this.name = name;
	}
	public SQLQueryParameter(String name, List<AbstractParameter> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	
	public String getName() {
		return name;
	}
	public List<AbstractParameter> getParameters() {
		return parameters;
	}
}
