package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar extends DBDataType{
	
	public Varchar() {
		super(12, 1024);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
