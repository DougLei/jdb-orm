package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NChar extends DBDataType{
	private static final NChar instance = new NChar();
	public static final NChar singleInstance() {
		return instance;
	}
	
	private NChar() {
		super((short)-15, (short)4000);
	}
}
