package com.douglei.orm.core.dialect.impl.mysql.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Mediumtext extends DBDataType{
	private static final Mediumtext instance = new Mediumtext();
	public static final Mediumtext singleInstance() {
		return instance;
	}
	
	private Mediumtext() {
		super((short)-1);
	}
}
