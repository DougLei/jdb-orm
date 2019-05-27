package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Clob extends DBDataType{
	private static final short SQL_TYPE = 2005;
	private static final Clob instance = new Clob();
	public static final Clob singleInstance() {
		return instance;
	}
	
	private Clob() {
		super(SQL_TYPE);
	}
}
