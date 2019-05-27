package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Cursor extends DBDataType{
	private static final short SQL_TYPE = -10;
	private static final Cursor instance = new Cursor();
	public static final Cursor singleInstance() {
		return instance;
	}
	
	private Cursor() {
		super(SQL_TYPE);
	}
}
