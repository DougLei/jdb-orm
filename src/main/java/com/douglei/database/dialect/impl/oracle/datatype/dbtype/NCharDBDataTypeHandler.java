package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

/**
 * 
 * @author DougLei
 */
class NCharDBDataTypeHandler extends Varchar2DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "nchar";
	}
	
	@Override
	public int getSqlType() {
		return -15;
	}
}
