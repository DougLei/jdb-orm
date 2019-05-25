package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

/**
 * 
 * @author DougLei
 */
class NCharDBDataTypeHandler extends VarcharDBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "nchar";
	}

	@Override
	public int getSqlType() {
		return -15;
	}
}
