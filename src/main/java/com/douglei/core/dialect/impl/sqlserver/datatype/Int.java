package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Int extends DBDataType{
	private static final Int instance = new Int();
	public static final Int singleInstance() {
		return instance;
	}
	
	private Int() {
		super((short)4);
	}
}
