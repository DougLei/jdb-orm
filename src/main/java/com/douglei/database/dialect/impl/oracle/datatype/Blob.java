package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Blob extends DBDataType{
	private static final short SQL_TYPE = 2004;
	private static final Blob instance = new Blob();
	public static final Blob singleInstance() {
		return instance;
	}
	
	private Blob() {
		super(SQL_TYPE);
	}
}
