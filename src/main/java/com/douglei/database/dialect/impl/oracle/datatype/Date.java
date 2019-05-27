package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Date extends DBDataType{
	private static final short SQL_TYPE = 93;
	private static final Date instance = new Date();
	public static final Date singleInstance() {
		return instance;
	}
	
	private Date() {
		super(SQL_TYPE);
	}
}
