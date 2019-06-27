package com.douglei.orm.core.dialect.impl.mysql.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Mediumblob extends DBDataType{
	private static final long serialVersionUID = 3490278797989576474L;
	
	private static final Mediumblob instance = new Mediumblob();
	public static final Mediumblob singleInstance() {
		return instance;
	}
	
	private Mediumblob() {
		super((short)-4);
	}
}
