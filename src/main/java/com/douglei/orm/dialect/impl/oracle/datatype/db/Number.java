package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Number extends DBDataType{
	private static final Number singleton = new Number();
	public static Number getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Number() {
		super(2, 38, 38);
	}
}
