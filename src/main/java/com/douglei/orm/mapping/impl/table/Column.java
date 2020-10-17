package com.douglei.orm.mapping.impl.table;

import java.io.Serializable;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Column implements Serializable{
	private static final long serialVersionUID = 673309519157633457L;
	
	private String name;
	private DBDataType dbDataType;
	
	public Column(String name, DBDataType dbDataType) {
		this.name = name;
		this.dbDataType = dbDataType;
	}
	
	public String getName() {
		return name;
	}
	public DBDataType getDbDataType() {
		return dbDataType;
	}
}
