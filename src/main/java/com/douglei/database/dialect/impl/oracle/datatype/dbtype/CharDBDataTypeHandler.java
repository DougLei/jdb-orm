package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
class CharDBDataTypeHandler extends Varchar2DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return OracleDBType.CHAR.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.CHAR.getSqlType();
	}
}
