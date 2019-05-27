package com.douglei.database.dialect.impl.mysql.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Bigint extends DBDataType{
	private static final short SQL_TYPE = -5;
	private static final Bigint instance = new Bigint();
	public static final Bigint singleInstance() {
		return instance;
	}
	
	private Bigint() {
		super(SQL_TYPE);
	}
}
