package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Decimal extends DBDataType{
	private static final short SQL_TYPE = 3;
	private static final Decimal instance = new Decimal();
	public static final Decimal singleInstance() {
		return instance;
	}
	
	private Decimal() {
		super(SQL_TYPE);
	}
}
