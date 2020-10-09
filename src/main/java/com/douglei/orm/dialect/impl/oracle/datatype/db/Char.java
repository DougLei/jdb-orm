package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Char extends DBDataType{
	private static final Char singleton = new Char();
	public static Char getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Char() {
		super(1, 2000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
