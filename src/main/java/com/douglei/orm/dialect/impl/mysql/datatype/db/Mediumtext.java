package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Mediumtext extends DBDataType{
	private static final Mediumtext singleton = new Mediumtext();
	public static Mediumtext getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Mediumtext() {
		super(-1);
	}
}
