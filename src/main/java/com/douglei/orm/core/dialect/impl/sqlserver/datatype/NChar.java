package com.douglei.orm.core.dialect.impl.sqlserver.datatype;

import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NChar extends DBDataType{
	private static final long serialVersionUID = 951546294841911284L;
	private static final NChar instance = new NChar();
	public static final NChar singleInstance() {
		return instance;
	}
	
	private NChar() {
		super((short)-15, (short)4000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
