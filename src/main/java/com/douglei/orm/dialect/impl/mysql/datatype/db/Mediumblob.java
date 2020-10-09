package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Mediumblob extends DBDataType{
	private static final Mediumblob singleton = new Mediumblob();
	public static Mediumblob getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Mediumblob() {
		super(-4);
	}
}
