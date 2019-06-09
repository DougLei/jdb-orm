package com.douglei.orm.core.dialect.impl.mysql.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

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
