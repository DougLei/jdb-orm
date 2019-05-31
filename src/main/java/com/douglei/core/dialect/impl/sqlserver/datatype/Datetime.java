package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Datetime extends DBDataType{
	private static final Datetime instance = new Datetime();
	public static final Datetime singleInstance() {
		return instance;
	}
	
	private Datetime() {
		super((short)93);
	}
}
