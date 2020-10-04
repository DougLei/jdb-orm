package com.douglei.orm.dialect.impl.mysql.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Char extends DBDataType{
	private static final long serialVersionUID = 3187114381786696532L;
	
	private static final Char instance = new Char();
	public static final Char singleInstance() {
		return instance;
	}
	
	private Char() {
		super((short)1, (short)255);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
