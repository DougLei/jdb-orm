package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Number extends DBDataType{
	private static final short SQL_TYPE = 2;
	private static final Number instance = new Number();
	public static final Number singleInstance() {
		return instance;
	}
	
	private Number() {
		super(SQL_TYPE);
	}
}
