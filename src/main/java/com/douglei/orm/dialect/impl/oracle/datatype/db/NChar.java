package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NChar extends DBDataType{
	
	public NChar() {
		super(-15, 1000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
