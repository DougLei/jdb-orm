package com.douglei.orm.dialect.impl.sqlserver.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Smallint extends DBDataType{
	private static final long serialVersionUID = 7502589663582867843L;
	private static final Smallint instance = new Smallint();
	public static final Smallint singleInstance() {
		return instance;
	}
	
	private Smallint() {
		super((short)5);
	}
}
