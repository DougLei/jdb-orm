package com.douglei.database.dialect.impl.oracle.datatype.handler;

import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;

/**
 * oracle datatype handler mapping
 * @author DougLei
 */
public class OracleDataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(OracleDBType.VARCHAR2.getTypeName());
	}
}
