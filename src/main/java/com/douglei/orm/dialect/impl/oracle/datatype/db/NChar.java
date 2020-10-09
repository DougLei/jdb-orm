package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NChar extends DBDataType{
	private static final NChar singleton = new NChar();
	public static NChar getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private NChar() {
		super(-15, 1000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
