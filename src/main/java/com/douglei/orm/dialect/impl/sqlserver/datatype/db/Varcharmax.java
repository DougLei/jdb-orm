package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Varcharmax extends DBDataType{
	
	public Varcharmax() {
		super(12);
		super.typeName = "VARCHAR";
	}
	
	@Override
	public String getSqlStatement(int length, int precision) {
		return typeName + "(max)";
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
