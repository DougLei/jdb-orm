package com.douglei.orm.dialect.impl.sqlserver.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar extends DBDataType{
	private static final long serialVersionUID = -2793391591239636604L;
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
