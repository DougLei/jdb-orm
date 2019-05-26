package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
class CharDBDataTypeHandler extends VarcharDBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return MySqlDBType.CHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.CHAR.getSqlType();
	}
}
