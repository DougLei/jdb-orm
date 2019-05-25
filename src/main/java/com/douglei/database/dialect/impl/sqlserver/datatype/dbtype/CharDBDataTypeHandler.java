package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

/**
 * 
 * @author DougLei
 */
class CharDBDataTypeHandler extends VarcharDBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "char";
	}

	@Override
	public int getSqlType() {
		return 1;
	}
}
