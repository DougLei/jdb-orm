package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Date extends DBDataType{
	private static final Date singleton = new Date();
	public static Date getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Date() {
		super(93);
	}
}
