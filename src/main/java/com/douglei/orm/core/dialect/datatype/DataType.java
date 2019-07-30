package com.douglei.orm.core.dialect.datatype;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	STRING,
	NSTRING,
	CHAR,
	NCHAR,
	
	BYTE,
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
		if(StringUtil.isEmpty(type)) {
			return STRING;
		}
		type = type.toUpperCase();
		DataType[] dts = DataType.values();
		for (DataType dt : dts) {
			if(dt.name().equals(type)) {
				return dt;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name();
	}
}
