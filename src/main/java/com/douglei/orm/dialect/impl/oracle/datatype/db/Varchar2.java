package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varchar2 extends DBDataType{
	
	public Varchar2() {
		super(12, 4000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
