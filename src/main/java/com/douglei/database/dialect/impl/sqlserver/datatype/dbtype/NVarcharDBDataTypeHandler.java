package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

/**
 * 
 * @author DougLei
 */
class NVarcharDBDataTypeHandler extends VarcharDBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "nvarchar";
	}

	@Override
	public int getSqlType() {
		return -9;
	}
}
