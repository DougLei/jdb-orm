package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Cursor extends DBDataType{
	private static final Cursor singleton = new Cursor();
	public static Cursor getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Cursor() {
		super(-10);
	}
}
