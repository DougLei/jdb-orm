package com.douglei.orm.core.dialect.impl.oracle.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Cursor extends DBDataType{
	private static final Cursor instance = new Cursor();
	public static final Cursor singleInstance() {
		return instance;
	}
	
	private Cursor() {
		super((short)-10);
	}
}
