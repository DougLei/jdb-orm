package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NChar extends DBDataType{
	private static final short SQL_TYPE = -15;
	private static final NChar instance = new NChar();
	public static final NChar singleInstance() {
		return instance;
	}
	
	private NChar() {
		super(SQL_TYPE);
	}
}
