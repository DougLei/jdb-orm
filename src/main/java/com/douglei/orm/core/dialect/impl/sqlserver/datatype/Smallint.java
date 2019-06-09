package com.douglei.orm.core.dialect.impl.sqlserver.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Smallint extends DBDataType{
	private static final Smallint instance = new Smallint();
	public static final Smallint singleInstance() {
		return instance;
	}
	
	private Smallint() {
		super((short)5);
	}
}
