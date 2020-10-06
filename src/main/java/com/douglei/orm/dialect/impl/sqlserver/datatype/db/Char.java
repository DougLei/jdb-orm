package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Char extends DBDataType{
	
	public Char() {
		super(1, 8000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
