package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Clob extends DBDataType{
	private static final Clob instance = new Clob();
	public static final Clob singleInstance() {
		return instance;
	}
	
	private Clob() {
		super((short)2005);
	}
}
