package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Date extends DBDataType{
	private static final Date instance = new Date();
	public static final Date singleInstance() {
		return instance;
	}
	
	private Date() {
		super((short)93);
	}
}
