package com.douglei.orm.core.dialect.datatype;

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

	public static DataType toValue(String type) {
		type = type.toUpperCase();
		DataType[] dts = DataType.values();
		for (DataType dt : dts) {
			if(dt.name().equals(type)) {
				return dt;
			}
		}
		return null;
	}
}
