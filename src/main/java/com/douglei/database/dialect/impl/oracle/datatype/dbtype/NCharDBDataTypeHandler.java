package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
class NCharDBDataTypeHandler extends Varchar2DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return OracleDBType.NCHAR.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.NCHAR.getSqlType();
	}
}
