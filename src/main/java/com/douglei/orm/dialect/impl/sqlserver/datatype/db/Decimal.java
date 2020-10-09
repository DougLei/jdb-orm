package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Decimal extends DBDataType{
	private static final Decimal singleton = new Decimal();
	public static Decimal getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Decimal() {
		super(3, 38, 38);
	}
}
