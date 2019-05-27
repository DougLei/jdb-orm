package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar2 extends DBDataType{
	private static final short SQL_TYPE = 12;
	private static final Varchar2 instance = new Varchar2();
	public static final Varchar2 singleInstance() {
		return instance;
	}
	
	private Varchar2() {
		super(SQL_TYPE);
	}
}
