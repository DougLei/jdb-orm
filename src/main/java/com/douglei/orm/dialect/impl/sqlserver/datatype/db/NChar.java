package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NChar extends DBDataType{
	
	public NChar() {
		super(-15, 4000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
