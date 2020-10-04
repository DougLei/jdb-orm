package com.douglei.orm.dialect.impl.mysql.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Mediumtext extends DBDataType{
	private static final long serialVersionUID = -7514786876124921380L;
	
	private static final Mediumtext instance = new Mediumtext();
	public static final Mediumtext singleInstance() {
		return instance;
	}
	
	private Mediumtext() {
		super((short)-1);
	}
}
