package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar extends DBDataType{
	private static final short SQL_TYPE = 12;
	private static final Varchar instance = new Varchar();
	public static final Varchar singleInstance() {
		return instance;
	}
	
	private Varchar() {
		super(SQL_TYPE);
	}
}
