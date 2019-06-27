package com.douglei.orm.core.dialect.impl.oracle.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Char extends DBDataType{
	private static final long serialVersionUID = 119713084635072782L;
	private static final Char instance = new Char();
	public static final Char singleInstance() {
		return instance;
	}
	
	private Char() {
		super((short)1, (short)2000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
