package com.douglei.database.dialect.datatype;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	STRING,
	NSTRING,
	CHAR,
	NCHAR,
	
	SHORT,
	INTEGER,
	LONG,
	
	FLOAT,
	DOUBLE,
	
	DATE,
	
	CLOB,
	BLOB;
	
	private String name;
	private DataType() {
		this.name = name().toLowerCase();
	}
	
	public String getName() {
		return name;
	}
}
