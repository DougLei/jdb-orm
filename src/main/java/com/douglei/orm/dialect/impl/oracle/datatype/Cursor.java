package com.douglei.orm.dialect.impl.oracle.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Cursor extends DBDataType{
	private static final long serialVersionUID = -9100288308403902420L;
	private static final Cursor instance = new Cursor();
	public static final Cursor singleInstance() {
		return instance;
	}
	
	private Cursor() {
		super((short)-10);
	}
}
