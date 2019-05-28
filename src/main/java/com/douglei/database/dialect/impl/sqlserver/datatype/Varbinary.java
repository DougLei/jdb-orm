package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varbinary extends DBDataType{
	private static final Varbinary instance = new Varbinary();
	public static final Varbinary singleInstance() {
		return instance;
	}
	
	private Varbinary() {
		super((short)-3);
	}
}
