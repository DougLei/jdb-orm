package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar2 extends DBDataType{
	
	public NVarchar2() {
		super(-9, 2000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
