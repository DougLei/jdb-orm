package com.douglei.database.dialect.impl.mysql.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Datetime extends DBDataType{
	private static final short SQL_TYPE = 93;
	private static final Datetime instance = new Datetime();
	public static final Datetime singleInstance() {
		return instance;
	}
	
	private Datetime() {
		super(SQL_TYPE);
	}
}
