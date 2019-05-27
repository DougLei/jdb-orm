package com.douglei.database.dialect.impl.oracle.datatype.handler;

import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.Varchar2;

/**
 * oracle datatype handler mapping
 * @author DougLei
 */
public class OracleDataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(Varchar2.singleInstance().getTypeName());
	}
}
