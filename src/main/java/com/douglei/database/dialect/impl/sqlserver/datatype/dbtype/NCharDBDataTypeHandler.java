package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
class NCharDBDataTypeHandler extends VarcharDBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return SqlServerDBType.NCHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.NCHAR.getSqlType();
	}
}
