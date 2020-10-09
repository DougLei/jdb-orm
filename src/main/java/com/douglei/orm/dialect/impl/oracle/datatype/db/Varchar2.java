package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar2 extends DBDataType{
	private static final Varchar2 singleton = new Varchar2();
	public static Varchar2 getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varchar2() {
		super(12, 4000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
