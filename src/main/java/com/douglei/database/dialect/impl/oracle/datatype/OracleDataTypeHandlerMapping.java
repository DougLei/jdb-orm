package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.dbtype.Varchar2DBDataTypeHandler;

/**
 * oracle datatype handler mapping
 * @author DougLei
 */
public class OracleDataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return Varchar2DBDataTypeHandler.singleInstance();
	}
	
}
