package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

/**
 * 
 * @author DougLei
 */
class NVarchar2DBDataTypeHandler extends Varchar2DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "nvarchar2";
	}
	
	@Override
	public int getSqlType() {
		return -9;
	}
}
