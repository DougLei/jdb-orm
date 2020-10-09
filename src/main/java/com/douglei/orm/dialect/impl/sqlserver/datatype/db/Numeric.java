package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Numeric extends DBDataType{
	private static final Numeric singleton = new Numeric();
	public static Numeric getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Numeric() {
		super(2, 38, 38);
	}
}
