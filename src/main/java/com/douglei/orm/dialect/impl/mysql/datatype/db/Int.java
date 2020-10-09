package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Int extends DBDataType{
	private static final Int singleton = new Int();
	public static Int getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Int() {
		super(4);
	}
}
