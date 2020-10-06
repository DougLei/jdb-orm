package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar extends DBDataType{
	
	public Varchar() {
		super(12, 8000);
	}

	@Override
	public boolean isCharacterType() {
		return true;
	}
}
