package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

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
		super((short)1, (short)8000);
	}
}
