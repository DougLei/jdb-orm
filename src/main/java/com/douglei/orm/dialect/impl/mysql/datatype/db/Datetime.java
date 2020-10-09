package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Datetime extends DBDataType{
	private static final Datetime singleton = new Datetime();
	public static Datetime getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Datetime() {
		super(93);
	}
}
