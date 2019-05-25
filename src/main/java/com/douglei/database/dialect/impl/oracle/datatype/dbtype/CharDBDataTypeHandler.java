package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

/**
 * 
 * @author DougLei
 */
class CharDBDataTypeHandler extends Varchar2DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "char";
	}
	
	@Override
	public int getSqlType() {
		return 1;
	}
}
