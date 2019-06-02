package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

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
		super((short)12, (short)8000);
	}

	@Override
	public boolean isCharacterType() {
		return true;
	}
}
