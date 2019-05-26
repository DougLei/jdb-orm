package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
class NVarcharDBDataTypeHandler extends VarcharDBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return SqlServerDBType.NVARCHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.NVARCHAR.getSqlType();
	}
}
