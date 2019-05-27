package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Char extends DBDataType{
	private static final short SQL_TYPE = 1;
	private static final Char instance = new Char();
	public static final Char singleInstance() {
		return instance;
	}
	
	private Char() {
		super(SQL_TYPE);
	}
}
