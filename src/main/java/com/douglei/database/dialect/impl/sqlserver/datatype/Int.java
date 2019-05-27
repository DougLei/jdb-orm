package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Int extends DBDataType{
	private static final short SQL_TYPE = 4;
	private static final Int instance = new Int();
	public static final Int singleInstance() {
		return instance;
	}
	
	private Int() {
		super(SQL_TYPE);
	}
}
