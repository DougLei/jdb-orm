package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Blob extends DBDataType{
	private static final Blob singleton = new Blob();
	public static Blob getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Blob() {
		super(2004);
	}
}
