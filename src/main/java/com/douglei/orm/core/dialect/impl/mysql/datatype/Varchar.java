package com.douglei.orm.core.dialect.impl.mysql.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar extends DBDataType{
	private static final Varchar instance = new Varchar();
	public static final Varchar singleInstance() {
		return instance;
	}
	
	private Varchar() {
		super((short)12, (short)1024);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
