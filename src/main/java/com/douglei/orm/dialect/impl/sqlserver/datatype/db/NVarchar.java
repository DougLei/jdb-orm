package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar extends DBDataType{
	
	public NVarchar() {
		super(-9, 4000);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
