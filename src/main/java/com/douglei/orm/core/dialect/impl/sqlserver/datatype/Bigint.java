package com.douglei.orm.core.dialect.impl.sqlserver.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Bigint extends DBDataType{
	private static final Bigint instance = new Bigint();
	public static final Bigint singleInstance() {
		return instance;
	}
	
	private Bigint() {
		super((short)-5);
	}
}
