package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
class NVarchar2DBDataTypeHandler extends Varchar2DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return OracleDBType.NVARCHAR2.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.NVARCHAR2.getSqlType();
	}
}
