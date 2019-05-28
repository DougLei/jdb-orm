package com.douglei.database.dialect.impl.mysql.datatype;

import com.douglei.database.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Mediumblob extends DBDataType{
	private static final Mediumblob instance = new Mediumblob();
	public static final Mediumblob singleInstance() {
		return instance;
	}
	
	private Mediumblob() {
		super((short)-4);
	}
}
