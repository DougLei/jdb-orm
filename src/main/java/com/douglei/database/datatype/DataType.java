package com.douglei.database.datatype;

import java.util.Arrays;

/**
 * 数据类型处理器
 * @author DougLei
 */
public enum DataType {
	CHAR,
	NCHAR,
	STRING,
	NSTRING,
	
	BIGSTRING,
	BYTE,
	
	INTEGER,
	DOUBLE,
	
	BOOLEAN,
	
	DATE,
	DATETIME;
	
	public static DataType toValue(String value) {
		value = value.trim().toUpperCase();
		
		DataType[] dts = DataType.values();
		for (DataType dataType : dts) {
			if(dataType.name().equals(value)) {
				return dataType;
			}
		}
		throw new IllegalArgumentException("配置的值[\""+value+"\"]错误, 目前支持的值包括：["+Arrays.toString(dts)+"]");
	}
}
