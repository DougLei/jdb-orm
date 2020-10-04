package com.douglei.orm.dialect.impl.sqlserver.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Char extends DBDataType{
	private static final long serialVersionUID = 4266376154116670545L;
	private static final Char instance = new Char();
	public static final Char singleInstance() {
		return instance;
	}
	
	private Char() {
		super((short)1, (short)8000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
