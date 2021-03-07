package com.douglei.orm.mapping.impl.sql;

/**
 * 
 * @author DougLei
 */
public enum SqlNodeType {
	TEXT("#text"), 
	IF, 
	ELSE, 
	TRIM, 
	SWITCH, 
	FOREACH, 
	WHERE, 
	SET, 
	INCLUDE, 
	PARAMETER;
	
	private String name;
	private SqlNodeType() {
		this.name = name().toLowerCase();
	}
	private SqlNodeType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
