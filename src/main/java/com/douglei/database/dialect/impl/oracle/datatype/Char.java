package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Char extends DBDataType{
	private static final Char instance = new Char();
	public static final Char singleInstance() {
		return instance;
	}
	
	private Char() {
		super((short)1, (short)2000);
	}
}