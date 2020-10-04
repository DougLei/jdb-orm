package com.douglei.orm.dialect.impl.oracle.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NChar extends DBDataType{
	private static final long serialVersionUID = -2523693246264110904L;
	private static final NChar instance = new NChar();
	public static final NChar singleInstance() {
		return instance;
	}
	
	private NChar() {
		super((short)-15, (short)1000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
