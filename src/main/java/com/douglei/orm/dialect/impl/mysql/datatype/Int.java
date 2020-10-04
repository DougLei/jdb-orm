package com.douglei.orm.dialect.impl.mysql.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Int extends DBDataType{
	private static final long serialVersionUID = 6039609777198427950L;
	
	private static final Int instance = new Int();
	public static final Int singleInstance() {
		return instance;
	}
	
	private Int() {
		super((short)4);
	}
}
