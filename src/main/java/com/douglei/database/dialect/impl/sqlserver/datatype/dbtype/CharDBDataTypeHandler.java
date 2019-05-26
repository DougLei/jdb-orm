package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
class CharDBDataTypeHandler extends VarcharDBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return SqlServerDBType.CHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.CHAR.getSqlType();
	}
}
