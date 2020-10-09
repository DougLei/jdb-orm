package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Smallint extends DBDataType{
	private static final Smallint singleton = new Smallint();
	public static Smallint getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Smallint() {
		super(5);
	}
}
