package com.douglei.core.dialect.impl.mysql.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

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
